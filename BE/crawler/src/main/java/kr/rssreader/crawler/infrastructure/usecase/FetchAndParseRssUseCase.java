package kr.rssreader.crawler.infrastructure.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.rssreader.crawler.domain.RssFeed;
import kr.rssreader.crawler.domain.URL;

public interface FetchAndParseRssUseCase {

    List<RssFeed> fetch(List<URL> urls);

    CompletableFuture<List<RssFeed>> fetchWithRetry(String url, int maxRetries, long delayMillis);
}