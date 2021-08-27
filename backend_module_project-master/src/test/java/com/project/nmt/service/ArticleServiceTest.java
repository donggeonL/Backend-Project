package com.project.nmt.service;

import com.project.nmt.model.Article;
import com.project.nmt.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    public void init() {
        Article article1 = Article.builder()
                .keyword("헬로")
                .title("인사는 중요하다.")
                .url("/hello")
                .build();
        Article article2 = Article.builder()
                .keyword("헬로")
                .title("인사는 중요하다.")
                .url("/hello")
                .build();
        Article article3 = Article.builder()
                .keyword("헬로")
                .title("인사는 중요하다.")
                .url("/hello")
                .build();
        Article article4 = Article.builder()
                .keyword("헬로")
                .title("인사는 중요하다.")
                .url("/hello")
                .build();

        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

    }

    @Test
    public void test() {
        assertSame(articleRepository.findAll().size(), 4);

        articleService.scrapAll();
        List<Article> helloResult = articleRepository.findAllByKeyword("헬로");
        List<Article> potatoResult = articleRepository.findAllByKeyword("감자");

        assertSame(helloResult.size(), 0);
        assertSame(potatoResult.size(), 10);
    }

}