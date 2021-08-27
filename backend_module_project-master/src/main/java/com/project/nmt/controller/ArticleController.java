package com.project.nmt.controller;

import com.project.nmt.model.Article;
import com.project.nmt.model.Stock;
import com.project.nmt.service.ArticleService;
import com.project.nmt.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final StockService stockService;
    private final ArticleService articleService;

    @GetMapping("/article/{page}")
    public String getArticleMain(Model model, @PathVariable("page") int page) {
        int size = 4;
        String sort = "keyword";

        int stockSize = stockService.getStockSize(size);
        List<Stock> stocks = stockService.getPagingAndSortingStocks(page, size, sort);
        List<Article> articles = articleService.getAllArticles();

        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", stockSize);
        model.addAttribute("stockList", stocks);
        model.addAttribute("articleList", articles);

        return "article/article-main";
    }

}
