package com.fastcampus.fastcampusprojectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());
        return "articles/index";
    }
    @GetMapping("/{articleId}")
    public String article_detail(ModelMap map, @PathVariable(name = "articleId") Long articleId) {

        map.addAttribute("articles", null);
        map.addAttribute("articleComments", List.of());
        return "articles/detail";
    }



}
