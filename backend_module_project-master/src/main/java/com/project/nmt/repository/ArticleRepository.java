package com.project.nmt.repository;

import com.project.nmt.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByKeyword(String keyword);

}
