package kr.rssreader.feed_fetcher.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class URL {

    @NotBlank(message = "URL은 비어 있을 수 없습니다.")
    @Pattern(
        regexp = "^(https?|ftp)://[\\w.-]+(?:\\.[\\w\\.-]+)+(?:[/?#][^\\s]*)?$",
        message = "유효하지 않은 URL 형식입니다."
    )
    private String url;

    public URL(String url) {
        this.url = url;
    }
}
