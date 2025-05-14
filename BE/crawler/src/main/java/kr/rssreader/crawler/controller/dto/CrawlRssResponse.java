package kr.rssreader.crawler.controller.dto;

import java.time.LocalDateTime;
import kr.rssreader.crawler.domain.RssFeed;

public record CrawlRssResponse(
    String title,
    String link,
    LocalDateTime publishedAt
) {

    public static CrawlRssResponse from(RssFeed feed) {
        return new CrawlRssResponse(feed.getTitle(), feed.getLink(), feed.getPublishedAt());
    }
}