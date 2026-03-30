package com.framework.utils;

import com.framework.config.ConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public final class WaitUtil {
    public WebDriver driver;
    private static final long DEFAULT_TIMEOUT_SECONDS = parseLong(
            ConfigLoader.get("wait.timeout.seconds", "10"),
            10L);
    private static final long DEFAULT_POLL_MILLIS = parseLong(
            ConfigLoader.get("wait.poll.millis", "500"),
            500L);

    public WaitUtil(WebDriver driver) {
        this.driver = driver;
    }

    private static long parseLong(String raw, long fallback) {
        if (raw == null) {
            return fallback;
        }
        String v = raw.trim();
        if (v.isEmpty()) {
            return fallback;
        }
        try {
            return Long.parseLong(v);
        } catch (NumberFormatException ignored) {
            return fallback;
        }
    }

    private WebDriverWait newWait(long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        if (DEFAULT_POLL_MILLIS > 0) {
            wait.pollingEvery(Duration.ofMillis(DEFAULT_POLL_MILLIS));
        }
        return wait;
    }

    public WebElement waitForPresence(By locator) {
        return waitForPresence(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public WebElement waitForPresence(By locator, long seconds) {
        return newWait(seconds).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForAllPresence(By locator, long seconds) {
        return newWait(seconds).until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return waitForElementToBeVisible(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public WebElement waitForElementToBeVisible(By locator, long seconds) {
        return newWait(seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return waitForElementToBeClickable(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public WebElement waitForElementToBeClickable(By locator, long seconds) {
        return newWait(seconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementToBeClickableIgnoringStale(By locator) {
        return waitForElementToBeClickableIgnoringStale(locator, DEFAULT_TIMEOUT_SECONDS);
    }

    public WebElement waitForElementToBeClickableIgnoringStale(By locator, long seconds) {
        return newWait(seconds)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForText(By locator, String text, long seconds) {
        return newWait(seconds).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean waitForUrlContains(String text, long seconds) {
        return newWait(seconds).until(ExpectedConditions.urlContains(text));
    }

    public boolean waitForTitleContains(String text, long seconds) {
        return newWait(seconds).until(ExpectedConditions.titleContains(text));
    }

    public boolean waitForNumberOfWindowsToBe(int expectedCount, long seconds) {
        return newWait(seconds).until(ExpectedConditions.numberOfWindowsToBe(expectedCount));
    }

    public boolean isAlertPresent(long seconds) {
        try {
            long s = Math.max(0L, seconds);
            newWait(s).until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException | NoAlertPresentException ignored) {
            return false;
        }
    }

    public WebElement fluentWaitFor(By locator, long timeoutSeconds, long pollMillis) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollMillis))
                .ignoring(NoSuchElementException.class);
        return wait.until(d -> d.findElement(locator));
    }

    public void sleepMillis(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
