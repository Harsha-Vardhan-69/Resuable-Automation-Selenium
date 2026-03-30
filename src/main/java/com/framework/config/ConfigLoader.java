package com.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Properties;

public final class ConfigLoader {
    private static final Properties props = new Properties();
    private static final String DEFAULT_CONFIG = "config.properties";
    private static final String[] ENV_KEYS = { "env", "Environment" };

    static {
        String env = resolveExecutionEnvironment();
        try {
            // Always load defaults first.
            loadFromClasspath(DEFAULT_CONFIG, true);

            // If env is not set, try to get it from the main config file.
            if (env == null || env.isBlank()) {
                String configEnv = getConfiguredEnvironment();
                if (configEnv != null && !configEnv.isBlank()) {
                    env = configEnv.trim();
                }
            }

            // Overlay env-specific values if env is provided.
            if (env != null && !env.isBlank()) {
                loadFromClasspath("config." + env.toLowerCase(Locale.ENGLISH) + ".properties", true);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load configuration: " + e.getMessage());
        }
    }

    private static String resolveExecutionEnvironment() {
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = System.getenv("ENV");
        }
        if (env == null || env.isBlank()) {
            env = System.getenv("env");
        }
        return (env == null || env.isBlank()) ? null : env.trim();
    }

    private static String getConfiguredEnvironment() {
        for (String key : ENV_KEYS) {
            String value = props.getProperty(key);
            if (value != null && !value.isBlank()) {
                return value.trim();
            }
        }
        return null;
    }

    private static void loadFromClasspath(String fileName, boolean required) throws IOException {
        try (InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                if (required) {
                    throw new IOException("Required config file not found on classpath: " + fileName);
                }
                return;
            }
            props.load(is);
        }
    }

    /**
     * Get a config value, checking environment variables first (for secrets).
     * If the environment variable (uppercased, dots replaced with underscores)
     * exists, use it.
     * Otherwise, fall back to the properties file.
     */
    public static String get(String key, String defaultValue) {
        // Allow Maven/System property overrides: -Dkey=value
        String sysValue = System.getProperty(key);
        if (sysValue != null && !sysValue.isBlank()) {
            return sysValue;
        }

        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return props.getProperty(key, defaultValue);
    }

    public static String get(String key) {
        return get(key, null);
    }

    public static String getRequiredBaseUrl() {
        String raw = get("url", "");
        if (raw == null || raw.isBlank()) {
            throw invalidConfiguration(
                    "Missing required config key 'url'. Configure a real URL directly or select an environment in config.properties.");
        }

        return normalizeUrl(raw, "Config key 'url'");
    }

    public static String normalizeUrl(String raw) {
        return normalizeUrl(raw, "URL");
    }

    private static String normalizeUrl(String raw, String sourceLabel) {
        if (raw == null || raw.isBlank()) {
            throw invalidConfiguration(sourceLabel + " cannot be blank.");
        }

        String trimmed = raw.trim();
        String normalizedToken = trimmed.toLowerCase(Locale.ENGLISH);
        if (isPlaceholderValue(normalizedToken)) {
            if ("Config key 'url'".equals(sourceLabel)) {
                throw invalidConfiguration("Config key 'url' resolved to placeholder value '" + trimmed
                        + "'. Use the 'env' key in config.properties to select a real config.<env>.properties file or set a real URL.");
            }
            throw invalidConfiguration(sourceLabel + " resolved to placeholder value '" + trimmed + "'.");
        }

        String normalizedUrl = trimmed.matches("^[a-zA-Z][a-zA-Z0-9+.-]*://.*$") ? trimmed : "https://" + trimmed;

        try {
            URI uri = new URI(normalizedUrl);
            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || scheme.isBlank()) {
                throw invalidConfiguration("Resolved URL is missing a scheme: " + normalizedUrl);
            }
            if (host == null || host.isBlank()) {
                throw invalidConfiguration("Resolved URL is missing a host: " + normalizedUrl);
            }
            if (!"localhost".equalsIgnoreCase(host) && !host.contains(".")) {
                throw invalidConfiguration(
                        "Resolved URL host looks like a placeholder rather than a real address: " + normalizedUrl);
            }

            return normalizedUrl;
        } catch (URISyntaxException e) {
            throw invalidConfiguration("Resolved URL is malformed: " + normalizedUrl, e);
        }
    }

    private static boolean isPlaceholderValue(String value) {
        return "uat".equals(value)
                || "prod".equals(value)
                || "production".equals(value)
                || "qa".equals(value)
                || "test".equals(value)
                || "stage".equals(value)
                || "staging".equals(value)
                || "dev".equals(value)
                || "local".equals(value);
    }

    private static IllegalStateException invalidConfiguration(String message) {
        return new IllegalStateException(message);
    }

    private static IllegalStateException invalidConfiguration(String message, Exception cause) {
        return new IllegalStateException(message, cause);
    }
}
