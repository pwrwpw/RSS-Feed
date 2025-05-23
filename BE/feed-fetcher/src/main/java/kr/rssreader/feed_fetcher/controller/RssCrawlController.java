package kr.rssreader.feed_fetcher.controller;

import jakarta.validation.Valid;
import kr.rssreader.common.CommonApiResponse;
import kr.rssreader.feed_fetcher.controller.dto.CrawlRssRequest;
import kr.rssreader.feed_fetcher.controller.dto.CrawlRssResponse;
import kr.rssreader.feed_fetcher.infrastructure.usecase.FetchAndParseRssUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rss")
public class RssCrawlController implements RssCrawlSpec {

    private final FetchAndParseRssUseCase useCase;

    @PostMapping("/crawl")
    @ResponseStatus(HttpStatus.OK)
    public CommonApiResponse<CrawlRssResponse> crawl(@Valid @RequestBody CrawlRssRequest request) {
        CrawlRssResponse response = CrawlRssResponse.from(useCase.fetch(request.urls()));

        return CommonApiResponse.success(response);
    }
}