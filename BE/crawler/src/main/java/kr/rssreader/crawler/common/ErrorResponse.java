package kr.rssreader.crawler.common;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
    String code,
    String message,
    int status
) {

    public static ErrorResponse of(String code, String message, HttpStatus status) {
        return new ErrorResponse(code, message, status.value());
    }
}