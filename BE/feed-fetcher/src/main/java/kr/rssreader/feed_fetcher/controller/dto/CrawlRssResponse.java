package kr.rssreader.feed_fetcher.controller.dto;

import java.util.List;
import kr.rssreader.feed_fetcher.domain.RssFeed;

public record CrawlRssResponse(
    List<RssFeed> items
) {

    public static CrawlRssResponse from(List<RssFeed> items) {
        return new CrawlRssResponse(items);
    }
}