package kr.rssreader.feed_fetcher.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import kr.rssreader.feed_fetcher.domain.URL;


public record CrawlRssRequest(
    @NotEmpty(message = "urls 필드는 비어 있을 수 없습니다.")
    List<@Valid URL> urls
) {

}