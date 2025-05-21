package kr.rssreader.feed_fetcher.infrastructure.usecase;

import kr.rssreader.feed_fetcher.domain.RssFeed;
import kr.rssreader.feed_fetcher.domain.URL;
import kr.rssreader.feed_fetcher.infrastructure.RssFetcher;
import kr.rssreader.feed_fetcher.infrastructure.RssParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DefaultFetchAndParseRssUseCaseTest {

    @Test
    void 여러_URL을_정상적으로_크롤링한다() {
        // given
        String rawUrl1 = "https://rss1.com";
        String rawUrl2 = "https://rss2.com";
        URL url1 = new URL(rawUrl1);
        URL url2 = new URL(rawUrl2);

        String fakeXml = "<rss><channel></channel></rss>";
        RssFeed feed1 = RssFeed.create("테스트1", "https://link1", LocalDateTime.now());
        RssFeed feed2 = RssFeed.create("테스트2", "https://link2", LocalDateTime.now());

        RssFetcher fetcher = mock(RssFetcher.class);
        RssParser parser = mock(RssParser.class);

        when(fetcher.fetchRawXml(rawUrl1)).thenReturn(fakeXml);
        when(fetcher.fetchRawXml(rawUrl2)).thenReturn(fakeXml);
        when(parser.parse(fakeXml)).thenReturn(List.of(feed1)).thenReturn(List.of(feed2));

        FetchAndParseRssUseCase useCase = new DefaultFetchAndParseRssUseCase(fetcher, parser);
        List<URL> urls = List.of(url1, url2);

        // when
        List<RssFeed> result = useCase.fetch(urls);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("title").containsExactlyInAnyOrder("테스트1", "테스트2");

        verify(fetcher, times(1)).fetchRawXml(rawUrl1);
        verify(fetcher, times(1)).fetchRawXml(rawUrl2);
        verify(parser, times(2)).parse(fakeXml);
    }

    @Test
    void 실패한_URL은_결과에서_제외된다() {
        String rawUrl1 = "https://ok.com";
        String rawUrl2 = "https://fail.com";
        URL url1 = new URL(rawUrl1);
        URL url2 = new URL(rawUrl2);

        String fakeXml = "<rss><channel></channel></rss>";
        RssFeed feed = RssFeed.create("테스트", "https://link", LocalDateTime.now());

        RssFetcher fetcher = mock(RssFetcher.class);
        RssParser parser = mock(RssParser.class);

        when(fetcher.fetchRawXml(rawUrl1)).thenReturn(fakeXml);
        when(fetcher.fetchRawXml(rawUrl2)).thenThrow(new RuntimeException("접속 불가"));
        when(parser.parse(fakeXml)).thenReturn(List.of(feed));

        FetchAndParseRssUseCase useCase = new DefaultFetchAndParseRssUseCase(fetcher, parser);
        List<URL> urls = List.of(url1, url2);

        List<RssFeed> result = useCase.fetch(urls);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("테스트");

        int maxRetries = 3;
        verify(fetcher, times(1)).fetchRawXml(rawUrl1);
        verify(fetcher, times(maxRetries)).fetchRawXml(rawUrl2);
    }

    @Test
    void 빈_URL_목록_요청시_빈_리스트_반환() {
        RssFetcher fetcher = mock(RssFetcher.class);
        RssParser parser = mock(RssParser.class);

        FetchAndParseRssUseCase useCase = new DefaultFetchAndParseRssUseCase(fetcher, parser);

        List<RssFeed> result = useCase.fetch(List.of());

        assertThat(result).isEmpty();
        verifyNoInteractions(fetcher, parser);
    }
}