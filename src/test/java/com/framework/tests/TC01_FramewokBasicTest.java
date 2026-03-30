package com.framework.tests;

import com.framework.config.ConfigLoader;
import com.framework.pageLocators.HanoverHomePage;
import org.testng.annotations.Test;

public class TC01_FramewokBasicTest extends BaseTest {
    @Test(groups = { "smoke", "website" })
    public void frameworkBasicHanoverTest() {
        String hanoverUrl = ConfigLoader.get("hanover.url", ConfigLoader.getRequiredBaseUrl());

        e.openUrlAndValidate(hanoverUrl, HanoverHomePage.welcomeHeading, "Hanover home page",
                "Open the Hanover home page");
        e.validateOnPage(HanoverHomePage.homeInsuranceLink, "Hanover home page");
        e.validateOnPage(HanoverHomePage.autoInsuranceLink, "Hanover home page");
        e.validateOnPage(HanoverHomePage.contactAgentLink, "Hanover home page");
        e.screenshot("tc01_hanover_home");
    }
}