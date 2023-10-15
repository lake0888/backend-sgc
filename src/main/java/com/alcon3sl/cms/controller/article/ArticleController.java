package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "article")
public class ArticleController {

    public ResponseEntity<Page<Article>> findAll() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "{articleId}")
    public ResponseEntity<Article> findById(@PathVariable(name = "articleId") Long articleId) {
        return ResponseEntity.ok().build();
    }
}
