package kr.rssreader.feed_fetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FeedFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedFetcherApplication.class, args);
	}

}
