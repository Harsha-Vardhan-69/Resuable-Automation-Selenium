package com.framework.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

final class TestDataResourceLoader {
    private static final String TEST_RESOURCES_PREFIX = "src/test/resources/";

    private TestDataResourceLoader() {
    }

    static InputStream open(String resourcePath) throws IOException {
        String normalizedPath = normalize(resourcePath);
        InputStream classpathStream = TestDataResourceLoader.class.getClassLoader().getResourceAsStream(normalizedPath);
        if (classpathStream != null) {
            return classpathStream;
        }

        Path filePath = Paths.get(resourcePath);
        if (Files.exists(filePath)) {
            return Files.newInputStream(filePath);
        }

        throw new IOException(
                "Test data resource not found: " + resourcePath + " (classpath path: " + normalizedPath + ")");
    }

    static boolean exists(String resourcePath) {
        String normalizedPath = normalize(resourcePath);
        if (TestDataResourceLoader.class.getClassLoader().getResource(normalizedPath) != null) {
            return true;
        }
        return Files.exists(Paths.get(resourcePath));
    }

    static String normalize(String resourcePath) {
        if (resourcePath == null) {
            throw new IllegalArgumentException("Resource path cannot be null.");
        }

        String normalizedPath = resourcePath.trim().replace('\\', '/');
        while (normalizedPath.startsWith("./")) {
            normalizedPath = normalizedPath.substring(2);
        }
        if (normalizedPath.startsWith("/")) {
            normalizedPath = normalizedPath.substring(1);
        }
        if (normalizedPath.startsWith(TEST_RESOURCES_PREFIX)) {
            normalizedPath = normalizedPath.substring(TEST_RESOURCES_PREFIX.length());
        }
        return normalizedPath;
    }
}