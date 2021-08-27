package com.project.nmt.service;

import com.project.nmt.jsoup.ArticleCrawler;
import com.project.nmt.model.Article;
import com.project.nmt.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCrawler articleCrawler;

    public List<Article> scrapAll() {
        articleRepository.deleteAll();

        List<Article> articles = articleCrawler.scrapAll();
        for (Article article : articles) {
            articleRepository.save(article);
        }

        return articles;
    }

    public List<Article> getArticlesByKeyword(String keyword) {
        return articleRepository.findAllByKeyword(keyword);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

}
