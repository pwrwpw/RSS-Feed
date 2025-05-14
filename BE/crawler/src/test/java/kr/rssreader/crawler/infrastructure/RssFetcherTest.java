package kr.rssreader.crawler.infrastructure;

import kr.rssreader.crawler.common.RssException;
import kr.rssreader.crawler.common.RssErrorCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RssFetcherTest {

    @Test
    void 정상적인_URL을_요청하면_XML을_받아온다() {
        RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
        String expectedXml = "<rss><channel></channel></rss>";
        String url = "https://test.com/rss";

        Mockito.when(mockRestTemplate.getForObject(url, String.class))
            .thenReturn(expectedXml);

        RssFetcher fetcher = new RssFetcher(mockRestTemplate);

        String xml = fetcher.fetchRawXml(url);

        assertThat(xml).isEqualTo(expectedXml);
    }

    @Test
    void 요청_실패시_예외를_던진다() {
        RestTemplate mockRestTemplate = Mockito.mock(RestTemplate.class);
        String url = "https://error.url";

        Mockito.when(mockRestTemplate.getForObject(url, String.class))
            .thenThrow(new RuntimeException("연결 실패"));

        RssFetcher fetcher = new RssFetcher(mockRestTemplate);

        assertThatThrownBy(() -> fetcher.fetchRawXml(url))
            .isInstanceOf(RssException.class)
            .hasMessage(RssErrorCode.FETCH_FAILED.getMessage());
    }
}