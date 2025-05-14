package kr.rssreader.crawler.controller;

import java.util.List;
import kr.rssreader.crawler.controller.dto.CrawlRssRequest;
import kr.rssreader.crawler.controller.dto.CrawlRssResponse;
import kr.rssreader.crawler.infrastructure.usecase.FetchAndParseRssUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rss")
public class RssCrawlController {

    private final FetchAndParseRssUseCase useCase;

    @PostMapping("/crawl")
    @ResponseStatus(HttpStatus.OK)
    public List<CrawlRssResponse> crawl(@RequestBody CrawlRssRequest request) {
        return useCase.fetch(request.urls()).stream()
            .map(CrawlRssResponse::from)
            .toList();
    }
}