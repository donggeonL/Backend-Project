package com.project.nmt.jsoup;

import com.project.nmt.model.Article;
import com.project.nmt.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArticleCrawler {

    private final StockRepository stockRepository;

    public List<Article> scrapAll() {
        final String BASE_URL = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=";
        final int ARTICLE_NUM = 10;
        List<String> keyWords = stockRepository.findAllStockKeyword();
        List<Article> articles = new ArrayList<>();

        for (String keyword : keyWords) {
            Document document = null;
            try {
                document = Jsoup.connect(BASE_URL + keyword).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert document != null;
            Elements select = document.select("a.news_tit");

            List<Article> result = select.stream()
                    .map(e -> Article.builder()
                            .keyword(keyword)
                            .title(e.attr("title"))
                            .url(e.attr("href"))
                            .build())
                    .limit(ARTICLE_NUM)
                    .collect(Collectors.toList());

            articles.addAll(result);
        }

        return articles;
    }

}
