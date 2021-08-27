package com.project.nmt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String keyword;
    private String title;
    private String url;

    @Builder
    public Article(String keyword, String title, String url) {
        this.keyword = keyword;
        this.title = title;
        this.url = url;
    }
}
