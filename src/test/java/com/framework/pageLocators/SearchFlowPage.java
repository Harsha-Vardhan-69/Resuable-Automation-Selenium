package com.framework.pageLocators;

import org.openqa.selenium.By;

public final class SearchFlowPage {
    private SearchFlowPage() {
    }

    public static final By googleSearchBox = By.name("q");

    public static final By youtubeSearchBox = By.name("search_query");
    public static final By youtubeSearchButton = By.cssSelector("button[aria-label='Search'], button[title='Search']");
    public static final By firstYoutubeVideo = By.cssSelector("ytd-video-renderer a#video-title");
}