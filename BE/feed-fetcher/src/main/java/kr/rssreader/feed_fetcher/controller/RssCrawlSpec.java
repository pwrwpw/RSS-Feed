package kr.rssreader.feed_fetcher.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.rssreader.common.CommonApiResponse;
import kr.rssreader.feed_fetcher.controller.dto.CrawlRssRequest;
import kr.rssreader.feed_fetcher.controller.dto.CrawlRssResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "RSS 크롤링", description = "RSS 피드를 받아와서 파싱 결과를 반환하는 API")
public interface RssCrawlSpec {

    @Operation(
        summary = "RSS 피드 크롤링",
        description = "입력된 RSS URL 목록을 기반으로 RSS 피드를 크롤링하고 파싱한 결과를 반환합니다."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "크롤링 성공"),
        @ApiResponse(responseCode = "400", description = "파싱 실패 또는 필수 태그 누락 (PARSE_FAILED, MISSING_TAG 등)"),
        @ApiResponse(responseCode = "422", description = "잘못된 title/link 값 (INVALID_TITLE, INVALID_LINK)"),
        @ApiResponse(responseCode = "502", description = "RSS 요청 중 오류 발생 (FETCH_FAILED)"),
        @ApiResponse(responseCode = "500", description = "내부 서버 오류 (INTERNAL_ERROR)")
    })
    @PostMapping(
        value = "/api/v1/rss/crawl",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    CommonApiResponse<CrawlRssResponse> crawl(@RequestBody @Valid CrawlRssRequest request);
}
