package kr.rssreader.crawler.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RssException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleRssException(RssException e) {
        RssErrorCode code = e.getErrorCode();
        log.warn("RssException 발생: [{}] {}", code.getCode(), code.getMessage());
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(CommonApiResponse.error(code));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleGenericException(Exception e) {
        log.error("Unhandled exception", e);
        RssErrorCode fallback = RssErrorCode.INTERNAL_ERROR;
        return ResponseEntity
            .status(fallback.getHttpStatus())
            .body(CommonApiResponse.error(fallback));
    }
}