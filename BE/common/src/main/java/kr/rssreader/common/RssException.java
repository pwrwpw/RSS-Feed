package kr.rssreader.common;

public class RssException extends RuntimeException {

    private final RssErrorCode errorCode;

    public RssException(RssErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public RssException(RssErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public RssErrorCode getErrorCode() {
        return errorCode;
    }
}