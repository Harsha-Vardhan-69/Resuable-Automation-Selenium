package com.framework.utils;

import com.framework.reports.ExtentReport;
import org.openqa.selenium.WebDriver;

public class TestElementUtil extends ElementUtil {

    public TestElementUtil(WebDriver driver) {
        super(driver);
    }

    public TestElementUtil() {
        this(null);
    }

    public static TestElementUtil forMockSteps() {
        return new TestElementUtil();
    }

    public String mockStep(String stepDescription) {
        String resolvedStep = stepDescription == null || stepDescription.isBlank()
                ? "Mock test step"
                : stepDescription.trim();
        String message = "[MOCK] " + resolvedStep;
        ExtentReport.logInfo(message);
        return message;
    }
}