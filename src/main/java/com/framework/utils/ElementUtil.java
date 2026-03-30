package com.framework.utils;

import com.framework.config.ConfigLoader;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.framework.listeners.TestListener;
import com.framework.reports.ExtentReport;

public class ElementUtil {

    private final WaitUtil waitUtil;
    public final WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        this.waitUtil = new WaitUtil(driver);
    }

    private void logStep(String... reportMessage) {
        if (reportMessage != null && reportMessage.length > 0 && reportMessage[0] != null
                && !reportMessage[0].isEmpty()) {
            ExtentReport.logInfo(reportMessage[0]);
        }
    }

    private WebElement visibleElement(By locator) {
        return waitUtil.waitForElementToBeVisible(locator);
    }

    private WebElement clickableElement(By locator) {
        waitUtil.waitForElementToBeVisible(locator);
        return waitUtil.waitForElementToBeClickable(locator);
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private String currentUrlSafely() {
        try {
            return driver != null ? driver.getCurrentUrl() : "";
        } catch (Throwable ignored) {
            return "";
        }
    }

    private String currentTitleSafely() {
        try {
            return driver != null ? driver.getTitle() : "";
        } catch (Throwable ignored) {
            return "";
        }
    }

    private String formatDateForInput(String inputDate) {
        try {
            java.time.LocalDate date = java.time.LocalDate.parse(inputDate);
            return date.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        } catch (Exception ignored) {
            return inputDate;
        }
    }

    public void sendKeys(By locator, String text, String... reportMessage) {
        WebElement element = visibleElement(locator);
        logStep(reportMessage);
        element.sendKeys(text);
    }

    public void sendKeysOptional(By locator, String text, String... reportMessage) {
        if (text == null || text.isEmpty()) {
            // No data to send, skip
            return;
        }
        sendKeys(locator, text, reportMessage);
    }

    public void sendKeys(By locator, Keys keys, String... reportMessage) {
        WebElement element = visibleElement(locator);
        logStep(reportMessage);
        element.sendKeys(keys);
    }

    public void type(By locator, String text, String... reportMessage) {
        WebElement element = visibleElement(locator);
        logStep(reportMessage);
        element.clear();
        element.sendKeys(text);
    }

    public void typeOptional(By locator, String text, String... reportMessage) {
        if (text == null || text.isEmpty()) {
            return;
        }
        type(locator, text, reportMessage);
    }

    public void enterDateOptional(By locator, String inputDate, String... reportMessage) {
        if (inputDate == null || inputDate.isEmpty()) {
            // No data to send, skip
            return;
        } else {
            enterDate(locator, inputDate, reportMessage);
        }
    }

    public void enterDate(By locator, String inputDate, String... reportMessage) {
        WebElement element = visibleElement(locator);
        logStep(reportMessage);
        String formattedDate = formatDateForInput(inputDate);
        element.sendKeys(formattedDate);
    }

    public void clickOptional(By locator, String text, String... reportMessage) {
        if (text == null || text.isEmpty()) {
            // No click, skip
            return;
        } else {
            click(locator, reportMessage);
        }

    }

    public void click(By locator, String... reportMessage) {
        WebElement element = clickableElement(locator);
        scrollIntoView(element);
        logStep(reportMessage);
        element.click();
    }

    public void clickIgnoringStale(By locator, String... reportMessage) {
        waitUtil.waitForElementToBeVisible(locator);
        WebElement element = waitUtil.waitForElementToBeClickableIgnoringStale(locator);
        scrollIntoView(element);
        logStep(reportMessage);
        element.click();
    }

    public void hover(By locator, String... reportMessage) {
        WebElement element = visibleElement(locator);
        scrollIntoView(element);
        logStep(reportMessage);
        new Actions(driver).moveToElement(element).perform();
    }

    public void clickByVisibleTextInRow(By rowLocator, String visibleText) {
        java.util.List<WebElement> rows = getElements(rowLocator);
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//*"));
            for (WebElement cell : cells) {
                if (cell.getText().trim().equalsIgnoreCase(visibleText.trim())) {
                    row.click();
                    return;
                }
            }
        }
        ExtentReport.logInfo("Clicking the row with visible text: " + visibleText);
        throw new RuntimeException("No row found with visible text: " + visibleText);
    }

    public void clear(By locator, String... reportMessage) {
        WebElement element = visibleElement(locator);
        logStep(reportMessage);
        element.clear();
    }

    public void openUrl(String url, String... reportMessage) {
        logStep(reportMessage);
        driver.get(ConfigLoader.normalizeUrl(url));
    }

    public void openUrlAndValidate(String url, By locator, String pageName, String... reportMessage) {
        openUrl(url, reportMessage);
        validateOnPage(locator, pageName);
    }

    public void openNewBrowserWindow(String... reportMessage) {
        logStep(reportMessage);
        driver.switchTo().newWindow(WindowType.WINDOW);
    }

    public void capturePage(String url, By locator, String pageName, String screenshotName, String... reportMessage) {
        openUrlAndValidate(url, locator, pageName, reportMessage);
        screenshot(screenshotName);
    }

    public void search(By inputLocator, String query, By submitLocator, String searchContext) {
        click(inputLocator, "Focus " + searchContext + " search box");
        type(inputLocator, query, "Enter '" + query + "' into " + searchContext + " search");
        if (submitLocator != null && isDisplayedFast(submitLocator)) {
            click(submitLocator, "Submit " + searchContext + " search for '" + query + "'");
        } else {
            sendKeys(inputLocator, Keys.ENTER, "Submit " + searchContext + " search for '" + query + "'");
        }
    }

    public void switchToFrame(By locator, String frameName, String... reportMessage) {
        WebElement frame = waitUtil.waitForPresence(locator);
        if (reportMessage != null && reportMessage.length > 0 && reportMessage[0] != null
                && !reportMessage[0].isEmpty()) {
            ExtentReport.logInfo(reportMessage[0]);
        } else if (frameName != null && !frameName.isBlank()) {
            ExtentReport.logInfo("Switching to frame '" + frameName + "'");
        }
        driver.switchTo().frame(frame);
    }

    public void switchToDefaultContent(String... reportMessage) {
        logStep(reportMessage);
        driver.switchTo().defaultContent();
    }

    public String getCurrentWindowHandle(String... reportMessage) {
        logStep(reportMessage);
        return driver.getWindowHandle();
    }

    public Set<String> getWindowHandlesSnapshot() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(driver.getWindowHandles()));
    }

    public String switchToWindow(String windowHandle, String... reportMessage) {
        logStep(reportMessage);
        driver.switchTo().window(windowHandle);
        return driver.getWindowHandle();
    }

    public String switchToNewWindow(Set<String> existingHandles, String... reportMessage) {
        Assert.assertNotNull(existingHandles, "Expected an existing window handle snapshot before switching tabs.");
        boolean newWindowReady = waitUtil.waitForNumberOfWindowsToBe(existingHandles.size() + 1, 10L);
        Assert.assertTrue(newWindowReady,
                "Expected a new browser window/tab to open. Existing handles=" + existingHandles.size());

        Set<String> currentHandles = new LinkedHashSet<>(driver.getWindowHandles());
        currentHandles.removeAll(existingHandles);
        Assert.assertFalse(currentHandles.isEmpty(), "Expected to find a newly opened browser window/tab.");

        String newWindowHandle = currentHandles.iterator().next();
        return switchToWindow(newWindowHandle, reportMessage);
    }

    public String openUrlInNewTab(String url, String... reportMessage) {
        Set<String> existingHandles = getWindowHandlesSnapshot();
        logStep(reportMessage);
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", ConfigLoader.normalizeUrl(url));
        return switchToNewWindow(existingHandles);
    }

    public String openUrlFromAttributeInNewTab(By locator, String attribute, String... reportMessage) {
        String targetUrl = getAttribute(locator, attribute);
        Assert.assertTrue(targetUrl != null && !targetUrl.isBlank(),
                "Expected locator " + locator + " to expose attribute '" + attribute + "'.");
        return openUrlInNewTab(targetUrl, reportMessage);
    }

    public String switchToWindowContainingUrl(String expectedUrlValue, String... reportMessage) {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (currentUrlSafely().toLowerCase().contains(expectedUrlValue.toLowerCase())) {
                logStep(reportMessage);
                return handle;
            }
        }
        driver.switchTo().window(originalHandle);
        Assert.fail("Expected a browser window/tab with URL containing '" + expectedUrlValue + "'.");
        return originalHandle;
    }

    public String switchToWindowContainingTitle(String expectedTitleValue, String... reportMessage) {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            try {
                if (waitUtil.waitForTitleContains(expectedTitleValue, 1L)) {
                    logStep(reportMessage);
                    return handle;
                }
            } catch (Exception ignored) {
            }
        }
        driver.switchTo().window(originalHandle);
        Assert.fail("Expected a browser window/tab with title containing '" + expectedTitleValue + "'.");
        return originalHandle;
    }

    public void closeCurrentWindowAndSwitchTo(String targetWindowHandle, String... reportMessage) {
        Assert.assertTrue(targetWindowHandle != null && !targetWindowHandle.isBlank(),
                "Expected a target window handle when closing the current browser window/tab.");
        logStep(reportMessage);
        driver.close();
        driver.switchTo().window(targetWindowHandle);
    }

    public void validateCurrentUrlContains(String expectedValue, String pageName) {
        try {
            waitUtil.waitForUrlContains(expectedValue, 10L);
        } catch (Exception ex) {
            String currentUrl = currentUrlSafely();

            String msg = (pageName != null && !pageName.isBlank())
                    ? ("Expected page '" + pageName + "' but URL did not contain '" + expectedValue + "'. url="
                            + currentUrl)
                    : ("Expected URL to contain '" + expectedValue + "' but was " + currentUrl);
            ExtentReport.logFail(msg);
            Assert.fail(msg);
            return;
        }

        if (pageName != null && !pageName.isBlank()) {
            ExtentReport.logPass("Currently on '" + pageName + "' as expected");
        }
    }

    public void validateCurrentTitleContains(String expectedValue, String pageName) {
        try {
            waitUtil.waitForTitleContains(expectedValue, 10L);
        } catch (Exception ex) {
            String currentTitle = currentTitleSafely();

            String msg = (pageName != null && !pageName.isBlank())
                    ? ("Expected page '" + pageName + "' but title did not contain '" + expectedValue + "'. title="
                            + currentTitle)
                    : ("Expected title to contain '" + expectedValue + "' but was " + currentTitle);
            ExtentReport.logFail(msg);
            Assert.fail(msg);
            return;
        }

        if (pageName != null && !pageName.isBlank()) {
            ExtentReport.logPass("Currently on '" + pageName + "' as expected");
        }
    }

    public void openUrlFromAttributeAndValidate(By locator, String attribute, String expectedUrlValue, String pageName,
            String... reportMessage) {
        String targetUrl = getAttribute(locator, attribute);
        Assert.assertTrue(targetUrl != null && !targetUrl.isBlank(),
                "Expected locator " + locator + " to expose attribute '" + attribute + "'.");
        openUrl(targetUrl, reportMessage);
        validateCurrentUrlContains(expectedUrlValue, pageName);
    }

    public void navigateBackUntilOnPage(String expectedUrlValue, int maxAttempts, By locator, String pageName) {
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            String currentUrl = currentUrlSafely();

            if (currentUrl.toLowerCase().contains(expectedUrlValue.toLowerCase())) {
                break;
            }
            driver.navigate().back();
        }

        validateCurrentUrlContains(expectedUrlValue, pageName);
        validateOnPage(locator, pageName);
    }

    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = waitUtil.waitForElementToBeVisible(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            ExtentReport.logInfo(locator.toString() + "Element is not displayed");
            return false;
        }
    }

    public boolean isDisplayedFast(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            if (elements == null || elements.isEmpty()) {
                return false;
            }
            for (WebElement element : elements) {
                try {
                    if (element != null && element.isDisplayed()) {
                        return true;
                    }
                } catch (Throwable ignored) {
                }
            }
            return false;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            waitUtil.waitForPresence(locator);
            return true;
        } catch (Exception e) {
            ExtentReport.logInfo(locator.toString() + "Element is not present");
            return false;
        }
    }

    public boolean isElementEnabled(By locator) {
        try {
            WebElement element = waitUtil.waitForElementToBeVisible(locator);
            return element.isEnabled();
        } catch (Exception e) {
            ExtentReport.logInfo(locator.toString() + "Element is not enabled");
            return false;
        }
    }

    public void selectDropdownByVisibleText(By locator, String visibleText, String... reportMessage) {
        WebElement dropdown = visibleElement(locator);
        Select select = new Select(dropdown);

        logStep(reportMessage);
        select.selectByVisibleText(visibleText);
    }

    public String getAttribute(By locator, String attribute) {
        WebElement element = visibleElement(locator);
        return element.getAttribute(attribute);
    }

    public List<WebElement> getElements(By locator) {
        return Collections.unmodifiableList(
                waitUtil.waitForAllPresence(locator, 10L));
    }

    public boolean waitForText(By locator, String text) {
        ExtentReport.logInfo("Waiting for text: " + text);
        return waitUtil.waitForText(locator, text, 10L);
    }

    public String getText(By locator) {
        WebElement element = visibleElement(locator);
        return element.getText();
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        ExtentReport.logInfo(url + " is the current page URL");
        return url;
    }

    public boolean isAlertPresent(int timeoutInSeconds) {
        return waitUtil.isAlertPresent(timeoutInSeconds);
    }

    public String getHiddenText(By locator) {
        return getHiddenText(locator, 10L);
    }

    public String getHiddenText(By locator, long timeoutSeconds) {
        try {
            WebElement el;
            try {
                el = waitUtil.waitForPresence(locator, timeoutSeconds);
            } catch (NoSuchElementException ex) {
                // In some screens, content may be inside an iframe; hidden context fields
                // are usually in the top document.
                try {
                    driver.switchTo().defaultContent();
                } catch (Throwable ignored) {
                }
                el = waitUtil.waitForPresence(locator, timeoutSeconds);
            }

            String t = el.getText();
            if (t != null && !t.isBlank()) {
                return t.trim();
            }
            String v = el.getAttribute("value");
            if (v != null && !v.isBlank()) {
                return v.trim();
            }
            String textContent = el.getAttribute("textContent");
            if (textContent != null && !textContent.isBlank()) {
                return textContent.trim();
            }
            return null;
        } catch (Throwable ignored) {
            return null;
        }
    }

    public void acceptAlert() throws InterruptedException {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            ExtentReport.logInfo("Alert appeared with text: " + alertText + "\n Alert accepted.");
            alert.accept();
        } catch (NoAlertPresentException ex) {
            ExtentReport.logInfo("No alert appeared to accept.");
        }

    }

    public void dismissAlert() throws InterruptedException {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            ExtentReport.logInfo("Alert appeared with text: " + alertText + "\n Alert dismmissed");
            alert.dismiss();
        } catch (NoAlertPresentException ex) {
            ExtentReport.logInfo("No alert appeared to dismiss.");
        }
    }

    public String getAlertText() throws InterruptedException {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            ExtentReport.logInfo("Alert appeared with text: " + alertText);
            return alertText;
        } catch (NoAlertPresentException ex) {
            ExtentReport.logInfo("No alert appeared to get text.");
            return null;
        }
    }

    public void sendKeysToAlert(String text) throws InterruptedException {
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
            ExtentReport.logInfo("Entering: " + text + " in popup Alert");
        } catch (NoAlertPresentException ex) {
            ExtentReport.logInfo("No alert appeared to send keys.");
        }
    }

    public void screenshot(String name) {
        String shot = ExtentReport.captureScreenshot(driver, name);
        Assert.assertNotNull(shot, name + " screenshot should be created");
    }

    public void scrollToViewAndScreenshot(By locator, String screenshotName) {
        WebElement element = visibleElement(locator);
        scrollIntoView(element);
        screenshot(screenshotName);
    }

    public void validateOnPage(By locator, String... Page) {
        String pageName = (Page != null && Page.length > 0) ? Page[0] : null;
        boolean present = isElementPresent(locator);

        if (!present) {
            String url = currentUrlSafely();

            String msg = (pageName != null && !pageName.isBlank())
                    ? ("Expected page '" + pageName + "' but element was not present. locator=" + locator + " url="
                            + url)
                    : ("Expected element was not present. locator=" + locator + " url=" + url);

            ExtentReport.logFail(msg);
            try {
                TestListener.attachStepArtifacts("validateOnPage_" + (pageName == null ? "page" : pageName));
            } catch (Throwable ignored) {
            }
            Assert.fail(msg);
            return;
        }

        if (pageName != null && !pageName.isEmpty()) {
            ExtentReport.logPass("Currently on '" + pageName + "' as expected");
        }
    }

}