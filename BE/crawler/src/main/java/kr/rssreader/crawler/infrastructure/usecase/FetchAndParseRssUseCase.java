package kr.rssreader.crawler.infrastructure.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import kr.rssreader.crawler.domain.RssFeed;

public interface FetchAndParseRssUseCase {

    List<RssFeed> fetch(List<String> urls);

    CompletableFuture<List<RssFeed>> fetchWithRetry(String url, int maxRetries, long delayMillis);
}