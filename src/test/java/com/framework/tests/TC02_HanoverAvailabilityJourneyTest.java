package com.framework.tests;

import com.framework.config.ConfigLoader;
import com.framework.data.CsvDataReader;
import com.framework.pageLocators.HanoverContactAgentPage;
import com.framework.pageLocators.HanoverHomePage;
import com.framework.pageLocators.HanoverProductPage;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC02_HanoverAvailabilityJourneyTest extends BaseTest {
    private static final String JOURNEY_DATA = "testdata/hanoverAvailabilityJourney.csv";

    @Test(groups = { "website", "regression" })
    public void hanoverAvailabilityJourney() throws IOException, CsvException {
        String hanoverUrl = ConfigLoader.get("hanover.url", ConfigLoader.getRequiredBaseUrl());
        List<Map<String, String>> pageRows = CsvDataReader.read(JOURNEY_DATA);
        Assert.assertFalse(pageRows.isEmpty(), "Expected Hanover availability data in " + JOURNEY_DATA + '.');

        Map<String, Map<String, String>> pagesByPath = indexByExpectedPath(pageRows);

        e.openUrlAndValidate(hanoverUrl, HanoverHomePage.welcomeHeading, "Hanover home page",
                "Open the Hanover home page");
        e.screenshot("tc02_hanover_home");

        for (Map<String, String> pageRow : pageRows) {
            openAndValidatePageFromHome(hanoverUrl, pageRow);
            validateAvailabilityLinkIfConfigured(pageRow, pagesByPath);
        }
    }

    private void openAndValidatePageFromHome(String hanoverUrl, Map<String, String> pageRow) {
        String pageName = requiredValue(pageRow, "pageName");
        String homeLinkPath = requiredValue(pageRow, "homeLinkPath");

        e.openUrlAndValidate(hanoverUrl, HanoverHomePage.welcomeHeading, "Hanover home page",
                "Return to the Hanover home page");
        e.validateOnPage(HanoverHomePage.linkByHref(homeLinkPath), "Hanover home page");
        e.openUrl(buildUrl(hanoverUrl, homeLinkPath),
            "Open the " + pageName + " screen from the Hanover home page link");
        validateConfiguredPage(pageRow);
        e.screenshot(requiredValue(pageRow, "screenshotName"));
    }

    private void validateAvailabilityLinkIfConfigured(Map<String, String> pageRow,
            Map<String, Map<String, String>> pagesByPath) {
        String hanoverUrl = ConfigLoader.get("hanover.url", ConfigLoader.getRequiredBaseUrl());
        String availabilityPath = optionalValue(pageRow, "availabilityPath");
        if (availabilityPath.isEmpty()) {
            return;
        }

        String pageName = requiredValue(pageRow, "pageName");
        Map<String, String> availabilityPage = pagesByPath.get(availabilityPath);
        Assert.assertNotNull(availabilityPage,
                "Expected availabilityPath '" + availabilityPath + "' to map to a CSV row.");

        e.validateOnPage(HanoverProductPage.contactAgentLink(availabilityPath), pageName);
        e.openUrl(buildUrl(hanoverUrl, availabilityPath),
            "Open the availability screen from the " + pageName + " page");
        validateConfiguredPage(availabilityPage);
        e.screenshot(requiredValue(pageRow, "screenshotName") + "_availability");
    }

    private void validateConfiguredPage(Map<String, String> pageRow) {
        String pageName = requiredValue(pageRow, "pageName");
        String expectedPath = requiredValue(pageRow, "expectedPath");
        String expectedTitle = requiredValue(pageRow, "expectedTitleFragment");
        String expectedHeading = requiredValue(pageRow, "expectedHeading");
        String pageType = requiredValue(pageRow, "pageType");

        e.validateCurrentUrlContains(expectedPath, pageName);
        e.validateCurrentTitleContains(expectedTitle, pageName);
        e.validateOnPage(HanoverProductPage.mainHeading(expectedHeading), pageName);

        if ("contact".equalsIgnoreCase(pageType)) {
            e.validateOnPage(HanoverContactAgentPage.zipCodeInput, pageName);
            return;
        }

        e.validateOnPage(HanoverProductPage.contactAgentLink(requiredValue(pageRow, "availabilityPath")), pageName);
    }

    private Map<String, Map<String, String>> indexByExpectedPath(List<Map<String, String>> pageRows) {
        Map<String, Map<String, String>> pagesByPath = new LinkedHashMap<>();
        for (Map<String, String> pageRow : pageRows) {
            String expectedPath = requiredValue(pageRow, "expectedPath");
            Assert.assertNull(pagesByPath.put(expectedPath, pageRow),
                    "Duplicate expectedPath value in Hanover CSV: " + expectedPath);
        }
        return pagesByPath;
    }

    private String requiredValue(Map<String, String> row, String key) {
        String value = optionalValue(row, key);
        Assert.assertFalse(value.isEmpty(), "Expected non-blank CSV value for column '" + key + "'.");
        return value;
    }

    private String optionalValue(Map<String, String> row, String key) {
        if (row == null || key == null) {
            return "";
        }
        return row.getOrDefault(key, "").trim();
    }

    private String buildUrl(String baseUrl, String relativePath) {
        return URI.create(baseUrl).resolve(relativePath).toString();
    }
}