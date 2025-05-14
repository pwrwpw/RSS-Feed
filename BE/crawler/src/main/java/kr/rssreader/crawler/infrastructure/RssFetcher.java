package kr.rssreader.crawler.infrastructure;

import kr.rssreader.crawler.common.RssErrorCode;
import kr.rssreader.crawler.common.RssException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RssFetcher {

    private final RestTemplate restTemplate;

    public String fetchRawXml(String feedUrl) {
        try {
            return restTemplate.getForObject(feedUrl, String.class);
        } catch (Exception e) {
            log.error("RSS 요청 실패: {}", feedUrl);
            log.debug("전체 스택:", e);
            throw new RssException(RssErrorCode.FETCH_FAILED, e);
        }
    }
}