package kr.rssreader.crawler.common;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import kr.rssreader.common.CommonApiResponse;
import kr.rssreader.common.RssErrorCode;
import kr.rssreader.common.RssException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CrawlerExceptionHandler {

    @ExceptionHandler(RssException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleRssException(RssException e) {
        RssErrorCode code = e.getErrorCode();
        log.warn("RssException 발생: [{}] {}", code.getCode(), code.getMessage());
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(CommonApiResponse.error(code));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = extractFieldErrors(ex.getFieldErrors());
        log.warn("검증 실패: {}", message);
        RssErrorCode code = RssErrorCode.INVALID_REQUEST;
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(CommonApiResponse.error(code, message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleInvalidJson(HttpMessageNotReadableException ex) {
        log.warn("JSON 파싱 실패", ex);
        RssErrorCode code = RssErrorCode.INVALID_REQUEST;
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(CommonApiResponse.error(code, "요청 본문이 올바르지 않습니다."));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("제약 조건 위반: {}", ex.getMessage());
        RssErrorCode code = RssErrorCode.INVALID_REQUEST;
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(CommonApiResponse.error(code, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<Void>> handleGenericException(Exception e) {
        log.error("Unhandled exception", e);
        RssErrorCode fallback = RssErrorCode.INTERNAL_ERROR;
        return ResponseEntity
            .status(fallback.getHttpStatus())
            .body(CommonApiResponse.error(fallback));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<CommonApiResponse<Void>> handleBindException(BindException ex) {
        String message = extractFieldErrors(ex.getFieldErrors());
        log.warn("바인딩 실패: {}", message);
        RssErrorCode code = RssErrorCode.INVALID_REQUEST;
        return ResponseEntity
            .status(code.getHttpStatus())
            .body(CommonApiResponse.error(code, message));
    }

    private String extractFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
    }
}