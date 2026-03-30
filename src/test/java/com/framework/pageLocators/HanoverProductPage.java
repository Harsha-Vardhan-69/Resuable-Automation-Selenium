package com.framework.pageLocators;

import org.openqa.selenium.By;

public final class HanoverProductPage {
    private HanoverProductPage() {
    }

    public static By mainHeading(String headingText) {
        return By.xpath("//main//h1[normalize-space()='" + headingText + "']");
    }

    public static By contactAgentLink(String relativePath) {
        return By.cssSelector("a[href='" + relativePath + "']");
    }
}