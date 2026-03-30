package com.framework.tests;

import com.framework.config.ConfigLoader;
import com.framework.driver.WebDriverFactory;
import com.framework.listeners.TestListener;
import com.framework.utils.ElementUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ TestListener.class })
public class BaseTest {
    protected WebDriver driver;
    protected ElementUtil e;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String base = ConfigLoader.getRequiredBaseUrl();
        driver = WebDriverFactory.getDriver();
        e = new ElementUtil(driver);
        driver.get(base);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverFactory.quitDriver();
    }

}
