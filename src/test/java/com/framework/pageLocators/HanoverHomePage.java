package com.framework.pageLocators;

import org.openqa.selenium.By;

public final class HanoverHomePage {
    private HanoverHomePage() {
    }

    public static final By welcomeHeading = By.xpath("//main//h1[normalize-space()='Welcome to Hanover.com!']");
    public static final By homeInsuranceLink = linkByHref("/individuals/products/home-insurance");
    public static final By autoInsuranceLink = linkByHref("/individuals/products/auto-insurance");
    public static final By contactAgentLink = linkByHref("/contact-agent");

    public static By linkByHref(String relativePath) {
        return By.cssSelector("a[href='" + relativePath + "']");
    }
}