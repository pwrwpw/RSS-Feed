package kr.rssreader.crawler.controller.dto;

import java.util.List;
import kr.rssreader.crawler.domain.RssFeed;

public record CrawlRssResponse(
    List<RssFeed> items
) {

    public static CrawlRssResponse from(List<RssFeed> items) {
        return new CrawlRssResponse(items);
    }
}