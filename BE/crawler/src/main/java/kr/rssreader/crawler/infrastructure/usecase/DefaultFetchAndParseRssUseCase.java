package kr.rssreader.crawler.infrastructure.usecase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import kr.rssreader.common.RssErrorCode;
import kr.rssreader.common.RssException;
import kr.rssreader.crawler.domain.RssFeed;
import kr.rssreader.crawler.infrastructure.RssFetcher;
import kr.rssreader.crawler.infrastructure.RssParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultFetchAndParseRssUseCase implements FetchAndParseRssUseCase {

    private final RssFetcher fetcher;
    private final RssParser parser;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public List<RssFeed> fetch(List<String> urls) {
        List<CompletableFuture<List<RssFeed>>> futures = urls.stream()
            .map(url -> fetchWithRetry(url, 3, 500))
            .toList();

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (Exception e) {
            log.warn("전체 크롤링 중 일부 실패가 발생했습니다.");
        }

        return futures.stream()
            .flatMap(future -> {
                try {
                    return future.join().stream();
                } catch (Exception e) {
                    log.warn("개별 크롤링 실패");
                    return Stream.empty();
                }
            })
            .toList();
    }

    @Override
    public CompletableFuture<List<RssFeed>> fetchWithRetry(String url, int maxAttempts, long delayMillis) {
        return tryFetch(url, 1, maxAttempts, delayMillis);
    }

    private CompletableFuture<List<RssFeed>> tryFetch(String url, int attempt, int maxAttempts, long delayMillis) {
        try {
            String xml = fetcher.fetchRawXml(url);
            List<RssFeed> result = parser.parse(xml);
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            if (attempt < maxAttempts) {
                log.warn("크롤링 실패 [{} / {}]: {}, 재시도 예약", attempt, maxAttempts, url);
                CompletableFuture<List<RssFeed>> retry = new CompletableFuture<>();
                scheduler.schedule(() -> {
                    tryFetch(url, attempt + 1, maxAttempts, delayMillis)
                        .whenComplete((res, err) -> {
                            if (err != null) {
                                retry.completeExceptionally(err);
                            } else {
                                retry.complete(res);
                            }
                        });
                }, delayMillis, TimeUnit.MILLISECONDS);
                return retry;
            } else {
                return CompletableFuture.failedFuture(new RssException(RssErrorCode.FETCH_FAILED, e));
            }
        }
    }
}