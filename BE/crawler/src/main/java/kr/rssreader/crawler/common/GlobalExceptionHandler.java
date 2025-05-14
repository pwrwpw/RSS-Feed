package kr.rssreader.crawler.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RssException.class)
    @ResponseStatus
    public ErrorResponse handleRssException(RssException e) {
        RssErrorCode code = e.getErrorCode();
        log.warn("RssException 발생: [{}] {}", code.getCode(), code.getMessage());
        return ErrorResponse.of(code.getCode(), code.getMessage(), code.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnhandledException(Exception e) {
        log.error("알 수 없는 예외 발생", e);
        RssErrorCode code = RssErrorCode.INTERNAL_ERROR;
        return ErrorResponse.of(code.getCode(), code.getMessage(), code.getHttpStatus());
    }
}