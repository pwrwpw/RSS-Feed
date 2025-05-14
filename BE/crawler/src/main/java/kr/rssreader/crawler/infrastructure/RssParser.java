package kr.rssreader.crawler.infrastructure;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import kr.rssreader.crawler.domain.RssFeed;
import kr.rssreader.crawler.common.RssErrorCode;
import kr.rssreader.crawler.common.RssException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RssParser {

    private static final DateTimeFormatter PUB_DATE_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;

    public List<RssFeed> parse(String xml) {
        List<RssFeed> feeds = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(xml, "", org.jsoup.parser.Parser.xmlParser());
            Elements items = doc.select("item");

            for (Element item : items) {
                String title = safeGetText(item, "title", true);
                String link = safeGetText(item, "link", true);
                String pubDateStr = safeGetText(item, "pubDate", false);

                LocalDateTime publishedAt = parsePubDate(pubDateStr);

                feeds.add(RssFeed.create(title, link, publishedAt));
            }

        } catch (RssException e) {
            throw e;
        } catch (Exception e) {
            log.error("RSS 파싱 실패", e);
            throw new RssException(RssErrorCode.PARSE_FAILED, e);
        }

        return feeds;
    }

    private String safeGetText(Element parent, String tagName, boolean required) {
        Element element = parent.selectFirst(tagName);
        if (element == null) {
            if (required) {
                throw new RssException(RssErrorCode.MISSING_TAG);
            } else {
                return null;
            }
        }
        return element.text();
    }

    private LocalDateTime parsePubDate(String pubDateStr) {
        if (pubDateStr == null) {
            return null;
        }
        try {
            return ZonedDateTime.parse(pubDateStr, PUB_DATE_FORMATTER).toLocalDateTime();
        } catch (Exception e) {
            log.warn("날짜 파싱 실패: {}", pubDateStr);
            return null;
        }
    }
}