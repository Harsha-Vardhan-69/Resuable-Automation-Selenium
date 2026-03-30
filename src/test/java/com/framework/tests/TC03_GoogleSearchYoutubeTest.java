package com.framework.tests;

import com.framework.pageLocators.SearchFlowPage;
import org.testng.annotations.Test;

public class TC03_GoogleSearchYoutubeTest extends BaseTest {

    @Test(groups = { "search" })
    public void googleSearchYoutubeJourney() {
        e.openUrlAndValidate("https://www.google.com", SearchFlowPage.googleSearchBox, "Google home page",
                "Open Google");
        e.screenshot("tc03_google_home");

        e.openUrlAndValidate("https://www.youtube.com", SearchFlowPage.youtubeSearchBox, "YouTube home page",
                "Open YouTube");
        e.validateCurrentUrlContains("youtube.com", "YouTube home page");
        e.screenshot("tc03_open_youtube");

        e.search(SearchFlowPage.youtubeSearchBox, "selenium", SearchFlowPage.youtubeSearchButton, "YouTube");
        e.validateOnPage(SearchFlowPage.firstYoutubeVideo, "YouTube search results");
        e.screenshot("tc03_youtube_search_selenium");

        e.openUrlAndValidate("https://www.youtube.com/results?search_query=selenium&sp=CAI%253D",
                SearchFlowPage.firstYoutubeVideo, "Latest YouTube search results",
                "Open YouTube latest results for selenium");
        e.screenshot("tc03_youtube_sort_latest");

        e.openUrlFromAttributeAndValidate(SearchFlowPage.firstYoutubeVideo, "href", "youtube.com/watch",
                "YouTube video page");

        e.navigateBackUntilOnPage("google", 4, SearchFlowPage.googleSearchBox, "Google home page");
        e.screenshot("tc03_back_to_google");
    }
}