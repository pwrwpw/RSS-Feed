package kr.rssreader.common;

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

    public static <T> CommonApiResponse<T> error(RssErrorCode errorCode, String customMessage) {
        CommonApiResponse<T> response = new CommonApiResponse<>();
        response.success = false;
        response.error = new ErrorBody(errorCode.getCode(), customMessage);
        return response;
    }


    public boolean isSuccess() {
        return success;
    }

    public T getBody() {
        return body;
    }

    public ErrorBody getError() {
        return error;
    }

    public static class ErrorBody {

        private final String code;
        private final String message;

        public ErrorBody(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}