package com.framework.listeners;

import com.framework.driver.WebDriverFactory;
import com.framework.reports.ExtentReport;
import com.framework.reports.ReportContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Enhanced TestListener: Attach logs, screenshots, and page sources for all
 * failed steps, not just test failures.
 * Provides a static utility for step-level artifact logging.
 */
public class TestListener implements ITestListener {
    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    private static IllegalStateException frameworkFailure(String action, Throwable cause) {
        LOGGER.error("Framework listener/reporting failure during {}", action, cause);
        return new IllegalStateException("Framework listener/reporting failure during " + action, cause);
    }

    public static void clearPerTestContext() {
        ExtentReport.clearCurrentTest();
        ReportContext.clearCurrentTestFolder();
    }

    /**
     * Call this method from any test step to attach artifacts (log, screenshot,
     * page source) to the report.
     * Example: TestListener.attachStepArtifacts("stepName");
     */
    public static String attachStepArtifacts(String stepName) {
        String screenshotPath = null;
        String methodName = stepName != null ? stepName : "Step";

        WebDriver driver = WebDriverFactory.getExistingDriver();
        if (driver == null) {
            return null;
        }

        boolean enableScreenshot = Boolean.parseBoolean(System.getProperty("report.attach.screenshot", "true"));
        if (enableScreenshot) {
            screenshotPath = ExtentReport.captureScreenshot(driver, methodName);
            if (screenshotPath != null) {
                Reporter.log("Screenshot: " + screenshotPath);
            }
        }

        boolean enablePageSource = Boolean.parseBoolean(System.getProperty("report.attach.pageSource", "true"));
        if (enablePageSource) {
            try {
                String pageSource = driver.getPageSource();
                String safe = methodName.replaceAll("[^a-zA-Z0-9_.-]", "_");
                java.nio.file.Path srcDir = ReportContext.getSourcesDirectory();
                java.nio.file.Path file = srcDir.resolve(safe + "_" + System.currentTimeMillis() + ".html");
                java.nio.file.Files.write(file, pageSource.getBytes());
                ExtentReport.attachPathLink("Page source", file.toAbsolutePath().toString());
            } catch (Exception e) {
                throw frameworkFailure("attachStepArtifacts", e);
            }
        }
        return screenshotPath;
    }

    public void onTestStart(ITestResult result) {
        clearPerTestContext();
        try {
            String methodName = result.getMethod().getMethodName();
            String classSimpleName = result.getTestClass().getRealClass().getSimpleName();
            String testCaseFolder = classSimpleName.split("_")[0]; // e.g., TC00 from TC00_LoginTest

            String screenshotFolder = (testCaseFolder + "_" + methodName).replaceAll("[^a-zA-Z0-9_.-]", "_");
            ReportContext.setCurrentTestFolder(screenshotFolder);

            ExtentReport.createTest(classSimpleName + ":" + methodName);
            ExtentReport.logInfo("Test started: " + classSimpleName + ":" + methodName);
            LOGGER.info("Test started: {}:{}", classSimpleName, methodName);
        } catch (RuntimeException e) {
            clearPerTestContext();
            throw frameworkFailure("onTestStart", e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            ExtentReport.logPass("Test passed: " + result.getMethod().getMethodName());
            LOGGER.info("Test passed: {}", result.getMethod().getMethodName());
        } catch (RuntimeException e) {
            throw frameworkFailure("onTestSuccess", e);
        } finally {
            clearPerTestContext();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String methodName = result.getMethod().getMethodName();
            Throwable throwable = result.getThrowable();

            if (throwable != null) {
                ExtentReport.logFail("Test failed: " + methodName + " - " + throwable);
                ExtentReport.logFail(throwable);
                LOGGER.error("Test failed: {}", methodName, throwable);
            } else {
                ExtentReport.logFail("Test failed: " + methodName);
                LOGGER.error("Test failed: {}", methodName);
            }

            // Attach artifacts once (avoid duplicate screenshots)
            String shot = attachStepArtifacts(methodName + "_Failure");
            if (shot != null) {
                result.setAttribute("screenshot", shot);
            }

            // Save stacktrace to a log file under the current run folder and attach a link
            if (throwable != null) {
                Path logsDir = ReportContext.getFailureLogsDirectory();
                String safe = methodName.replaceAll("[^a-zA-Z0-9_.-]", "_");

                StringWriter sw = new StringWriter();
                throwable.printStackTrace(new PrintWriter(sw));
                Path logFile = logsDir.resolve(safe + "_" + System.currentTimeMillis() + ".log");
                Files.writeString(logFile, sw.toString());
                ExtentReport.attachPathLink("Failure stacktrace", logFile.toAbsolutePath().toString());
            }

            // (Page source now attached in attachStepArtifacts)
        } catch (Exception e) {
            throw frameworkFailure("onTestFailure", e);
        } finally {
            clearPerTestContext();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            ExtentReport.logInfo("Test skipped: " + result.getMethod().getMethodName());
            LOGGER.info("Test skipped: {}", result.getMethod().getMethodName());
        } catch (RuntimeException e) {
            throw frameworkFailure("onTestSkipped", e);
        } finally {
            clearPerTestContext();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            LOGGER.info("Finishing test context: {}", context.getName());
            ExtentReport.flush();
        } catch (RuntimeException e) {
            throw frameworkFailure("onFinish", e);
        }
    }

}
