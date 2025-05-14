package kr.rssreader.crawler.infrastructure;

import kr.rssreader.crawler.domain.RssFeed;
import kr.rssreader.crawler.common.RssException;
import kr.rssreader.crawler.common.RssErrorCode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RssParserTest {

    private final RssParser parser = new RssParser();

    @Test
    void 올바른_XML을_파싱하면_RssFeed_리스트를_얻는다() {
        String xml = """
            <rss><channel>
            <item>
                <title>테스트 제목</title>
                <link>https://example.com</link>
                <pubDate>Wed, 15 May 2025 10:00:00 GMT</pubDate>
            </item>
            </channel></rss>
        """;

        List<RssFeed> result = parser.parse(xml);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    void title_태그가_없으면_예외가_발생한다() {
        String xml = """
            <rss><channel>
            <item>
                <link>https://example.com</link>
            </item>
            </channel></rss>
        """;

        assertThatThrownBy(() -> parser.parse(xml))
            .isInstanceOf(RssException.class)
            .hasMessageContaining(RssErrorCode.MISSING_TAG.getMessage());
    }

    @Test
    void pubDate가_잘못되었을_경우_null로_처리된다() {
        String xml = """
            <rss><channel>
            <item>
                <title>제목</title>
                <link>https://example.com</link>
                <pubDate>invalid-date</pubDate>
            </item>
            </channel></rss>
        """;

        List<RssFeed> result = parser.parse(xml);
        assertThat(result.get(0).getPublishedAt()).isNull();
    }
}