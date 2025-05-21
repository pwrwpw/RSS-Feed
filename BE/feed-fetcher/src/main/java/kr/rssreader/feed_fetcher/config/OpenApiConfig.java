package kr.rssreader.feed_fetcher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(
        @Value("${app.url}") String url
    ) {
        return new OpenAPI()
            .info(apiInfo())
            .servers(List.of(new Server().url(url).description("Default server")));
    }

    private Info apiInfo() {
        return new Info()
            .title("RSS Reader API Specification")
            .description("RSS Reader 서비스의 크롤러 API 문서입니다.")
            .version("1.0.0");
    }
}
