package kr.rssreader.common;

public enum RssErrorCode {

    FETCH_FAILED("RSS_001", "RSS 요청 중 오류가 발생했습니다.", 502),
    PARSE_FAILED("RSS_002", "RSS 파싱 중 오류가 발생했습니다.", 400),
    MISSING_TAG("RSS_003", "필수 태그가 누락되었습니다.", 400),
    INVALID_TITLE("RSS_004", "title은 비어 있을 수 없습니다.", 422),
    INVALID_LINK("RSS_005", "link는 비어 있을 수 없습니다.", 422),
    INTERNAL_ERROR("COMMON_500", "내부 서버 오류가 발생했습니다.", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    RssErrorCode(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}