package kr.rssreader.feed_fetcher.domain;

import java.time.LocalDateTime;
import kr.rssreader.common.RssErrorCode;
import kr.rssreader.common.RssException;
import lombok.Getter;

@Getter
public class RssFeed {

    private final String title;
    private final String link;
    private final LocalDateTime publishedAt;

    private RssFeed(String title, String link, LocalDateTime publishedAt) {
        validate(title, link);
        this.title = title;
        this.link = link;
        this.publishedAt = publishedAt;
    }

    public static RssFeed create(String title, String link, LocalDateTime publishedAt) {
        return new RssFeed(title, link, publishedAt);
    }

    private void validate(String title, String link) {
        if (title == null || title.isBlank()) {
            throw new RssException(RssErrorCode.INVALID_TITLE);
        }
        if (link == null || link.isBlank()) {
            throw new RssException(RssErrorCode.INVALID_LINK);
        }
    }
}
