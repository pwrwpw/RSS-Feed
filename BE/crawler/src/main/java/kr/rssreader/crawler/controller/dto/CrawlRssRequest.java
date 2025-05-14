package kr.rssreader.crawler.controller.dto;

import java.util.List;

public record CrawlRssRequest(
    List<String> urls
) {

}