package kr.rssreader.crawler.common;

import lombok.Getter;

@Getter
public class CommonApiResponse<T> {

    private boolean success;
    private T body;
    private ErrorBody error;

    private CommonApiResponse() {
    }

    public static <T> CommonApiResponse<T> success(T body) {
        CommonApiResponse<T> response = new CommonApiResponse<>();
        response.success = true;
        response.body = body;
        return response;
    }

    public static <T> CommonApiResponse<T> error(RssErrorCode errorCode) {
        CommonApiResponse<T> response = new CommonApiResponse<>();
        response.success = false;
        response.error = new ErrorBody(errorCode.getCode(), errorCode.getMessage());
        return response;
    }

    public record ErrorBody(String code, String message) {

    }
}
