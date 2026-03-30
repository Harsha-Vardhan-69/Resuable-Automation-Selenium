package com.framework.utils;

import com.framework.reports.ReportContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.nio.file.Path;

public final class ScreenshotUtil {
    private static final Logger LOGGER = LogManager.getLogger(ScreenshotUtil.class);
    private static final ThreadLocal<String> LAST_FAILURE_DETAIL = new ThreadLocal<>();

    private ScreenshotUtil() {
    }

    public static void setCurrentTestFolder(String folderName) {
        ReportContext.setCurrentTestFolder(folderName);
    }

    public static void clearCurrentTestFolder() {
        ReportContext.clearCurrentTestFolder();
    }

    public static String getCurrentTestFolder() {
        return ReportContext.getCurrentTestFolder();
    }

    public static String getLastFailureDetail() {
        return LAST_FAILURE_DETAIL.get();
    }

    public static void clearLastFailureDetail() {
        LAST_FAILURE_DETAIL.remove();
    }

    public static String consumeLastFailureDetail() {
        String detail = LAST_FAILURE_DETAIL.get();
        LAST_FAILURE_DETAIL.remove();
        return detail;
    }

    public static String takeScreenshot(WebDriver driver, String name) {
        clearLastFailureDetail();
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String safe = name.replaceAll("[^a-zA-Z0-9_.-]", "_");
            Path dir = ReportContext.getScreenshotDirectory("Misc");
            String fileName = safe + "_" + System.currentTimeMillis() + ".png";
            Path dest = dir.resolve(fileName);
            FileUtils.copyFile(src, dest.toFile());
            return ReportContext.relativizeFromExtentDirectory(dest);
        } catch (Exception e) {
            String detail = e.getClass().getSimpleName() + ": "
                    + (e.getMessage() == null || e.getMessage().isBlank() ? "<no message>" : e.getMessage());
            LAST_FAILURE_DETAIL.set(detail);
            LOGGER.error("Screenshot capture failed for {}", name, e);
            return null;
        }
    }
}
