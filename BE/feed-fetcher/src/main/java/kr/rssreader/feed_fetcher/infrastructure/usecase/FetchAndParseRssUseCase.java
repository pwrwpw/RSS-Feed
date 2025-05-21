package kr.rssreader.feed_fetcher.infrastructure.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.rssreader.feed_fetcher.domain.RssFeed;
import kr.rssreader.feed_fetcher.domain.URL;

public interface FetchAndParseRssUseCase {

    List<RssFeed> fetch(List<URL> urls);

    CompletableFuture<List<RssFeed>> fetchWithRetry(String url, int maxRetries, long delayMillis);
}