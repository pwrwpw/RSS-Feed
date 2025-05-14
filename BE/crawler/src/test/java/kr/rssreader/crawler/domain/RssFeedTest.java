package kr.rssreader.crawler.domain;

import kr.rssreader.common.RssErrorCode;
import kr.rssreader.common.RssException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class RssFeedTest {

    @Test
    void 유효한_데이터로_RssFeed_생성_성공() {
        RssFeed feed = RssFeed.create("제목", "https://link", LocalDateTime.now());

        assertThat(feed.getTitle()).isEqualTo("제목");
        assertThat(feed.getLink()).startsWith("https://");
    }

    @Test
    void 제목이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> RssFeed.create(null, "https://link", LocalDateTime.now()))
            .isInstanceOf(RssException.class)
            .hasMessage(RssErrorCode.INVALID_TITLE.getMessage());
    }

    @Test
    void 링크가_공백이면_예외가_발생한다() {
        assertThatThrownBy(() -> RssFeed.create("제목", " ", LocalDateTime.now()))
            .isInstanceOf(RssException.class)
            .hasMessage(RssErrorCode.INVALID_LINK.getMessage());
    }
}