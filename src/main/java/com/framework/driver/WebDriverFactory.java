package com.framework.driver;

import com.framework.config.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final List<String> SUPPORTED_BROWSERS = List.of("chrome", "edge");

    public static WebDriver getExistingDriver() {
        return driverThreadLocal.get();
    }

    public static WebDriver getDriver() {
        String browser = normalizeBrowserName(ConfigLoader.get("browser", "chrome"));
        WebDriver drv = driverThreadLocal.get();
        if (drv == null) {
            drv = createDriver(browser);
            driverThreadLocal.set(drv);
        }
        return drv;
    }

    private static WebDriver createDriver(String browser) {
        switch (browser) {
            case "edge":
                return createEdge();
            case "chrome":
            default:
                return createChrome();
        }
    }

    private static WebDriver createChrome() {
        boolean headless = isHeadlessConfigured();
        ChromeOptions options = buildChromeOptions(headless);
        try {
            WebDriverManager.chromedriver().setup();
            return createChromeDriver(options, headless);
        } catch (Throwable t) {
            String driverPath = findLocalDriver("chromedriver");
            if (driverPath != null) {
                System.setProperty("webdriver.chrome.driver", driverPath);
                return createChromeDriver(options, headless);
            }
            throw new RuntimeException("Failed to initialize ChromeDriver", t);
        }
    }

    private static WebDriver createEdge() {
        boolean headless = isHeadlessConfigured();
        EdgeOptions options = buildEdgeOptions(headless);
        try {
            WebDriverManager.edgedriver().setup();
            return createEdgeDriver(options, headless);
        } catch (Throwable t) {
            String driverPath = findLocalDriver("msedgedriver");
            if (driverPath != null) {
                System.setProperty("webdriver.edge.driver", driverPath);
                return createEdgeDriver(options, headless);
            }
            throw new RuntimeException("Failed to initialize EdgeDriver", t);
        }
    }

    static String normalizeBrowserName(String browser) {
        String normalized = browser == null ? "chrome" : browser.trim().toLowerCase();
        if (normalized.isEmpty()) {
            return "chrome";
        }
        if (SUPPORTED_BROWSERS.contains(normalized)) {
            return normalized;
        }
        throw new IllegalStateException("Unsupported browser '" + browser
                + "'. Supported browsers: chrome, edge.");
    }

    static ChromeOptions buildChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    static EdgeOptions buildEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    private static boolean isHeadlessConfigured() {
        return Boolean.parseBoolean(ConfigLoader.get("headless", "false"));
    }

    private static WebDriver createChromeDriver(ChromeOptions options, boolean headless) {
        WebDriver drv = new ChromeDriver(options);
        maximizeIfSupported(drv, headless);
        return drv;
    }

    private static WebDriver createEdgeDriver(EdgeOptions options, boolean headless) {
        WebDriver drv = new EdgeDriver(options);
        maximizeIfSupported(drv, headless);
        return drv;
    }

    private static void maximizeIfSupported(WebDriver drv, boolean headless) {
        if (headless) {
            return;
        }
        try {
            drv.manage().window().maximize();
        } catch (Throwable ignore) {
        }
    }

    private static String findLocalDriver(String baseName) {
        // check drivers folder at project root
        String exeExt = getExeExt();
        String normalizedBase = baseName.toLowerCase();
        String[] exactCandidates = { normalizedBase + exeExt, baseName + exeExt };
        File driversDir = new File(System.getProperty("user.dir"), "drivers");
        if (driversDir.exists() && driversDir.isDirectory()) {
            for (String c : exactCandidates) {
                File f = new File(driversDir, c);
                if (f.exists() && f.isFile()) {
                    return f.getAbsolutePath();
                }
            }

            File[] files = driversDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!f.isFile()) {
                        continue;
                    }
                    String name = f.getName().toLowerCase();
                    boolean extensionMatches = exeExt.isEmpty() || name.endsWith(exeExt);
                    if (extensionMatches && name.startsWith(normalizedBase)) {
                        return f.getAbsolutePath();
                    }
                }
            }
        }
        return null;
    }

    private static String getExeExt() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win") ? ".exe" : "";
    }

    public static void quitDriver() {
        WebDriver drv = driverThreadLocal.get();
        if (drv != null) {
            try {
                drv.quit();
            } catch (Throwable ignore) {
            }
            driverThreadLocal.remove();
        }
    }
}
