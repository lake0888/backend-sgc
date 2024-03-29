package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.article.Article;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.article.article.DBArticleService;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "article")
public class ArticleController {
    private final DBArticleService articleService;

    @Autowired
    public ArticleController(DBArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<Page<Article>> findAll(
            @RequestParam Optional<String> filter, Pageable pageable
    ) {
        var page = articleService.findAll(filter.orElse(""),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
                ));
        if (page.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = "{articleId}")
    public ResponseEntity<Article> findById(@PathVariable(name = "articleId") Long articleId) {
        var article = articleService.findById(articleId);
        if (article != null)
            return new ResponseEntity<>(article, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<URI> save(
            @RequestPart(name = "article") Article article,
            @RequestPart(name = "imageFile") MultipartFile imageFile,
            UriComponentsBuilder ucb) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        article.setImage(image);
        return new ResponseEntity<>(articleService.save(article, ucb), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{articleId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "articleId") Long articleId) {
        var article = articleService.deleteById(articleId);
        if (article != null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "{articleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateById(
            @PathVariable(name = "articleId") Long articleId,
            @RequestPart(name = "article") Article updateArticle,
            @RequestPart(name = "imageFile") MultipartFile imageFile
    ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        updateArticle.setImage(image);
        var article = articleService.updateById(articleId, updateArticle);
        if (article != null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "deleteAllById")
    public ResponseEntity<List<Article>> deleteAllById(@RequestParam(name = "listId") List<Long> listId) {
        var articleList = articleService.deleteAllById(listId);
        if (articleList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }
}
