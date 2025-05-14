package kr.rssreader.crawler.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RssErrorCode {

    FETCH_FAILED("RSS_001", "RSS 요청 중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    PARSE_FAILED("RSS_002", "RSS 파싱 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST),
    MISSING_TAG("RSS_003", "필수 태그가 누락되었습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TITLE("RSS_004", "title은 비어 있을 수 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    INVALID_LINK("RSS_005", "link는 비어 있을 수 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_ERROR("COMMON_500", "내부 서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    RssErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
