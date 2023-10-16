package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import com.alcon3sl.cms.services.article.kind_article.DBKindArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "kind_article")
public class KindArticleController {
    private final DBKindArticleService kindArticleService;

    @Autowired
    public KindArticleController(DBKindArticleService kindArticleService) {
        this.kindArticleService = kindArticleService;
    }

    @GetMapping
    public ResponseEntity<Page<KindArticle>> findAll(
            @RequestParam Optional<String> name, Pageable pageable
            ) {
        var page = kindArticleService.findAll(name.orElse(""),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
                ));
        if (page.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = "{kindArticleId}")
    public ResponseEntity<KindArticle> findById(@PathVariable(name = "kindArticleId") Long kindArticleId) {
        var kindArticle = kindArticleService.findById(kindArticleId);
        if (kindArticle != null)
            return new ResponseEntity<>(kindArticle, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<URI> save(@RequestBody KindArticle newKindArticle, UriComponentsBuilder ucb) {
        return new ResponseEntity<>(kindArticleService.save(newKindArticle, ucb), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{kindArticleId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "kindArticleId") Long kindArticleId) {
        var kindArticle = kindArticleService.deleteById(kindArticleId);
        if (kindArticle != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "{kindArticleId}")
    public ResponseEntity<Void> updateById(
            @PathVariable(name = "kindArticleId") Long kindArticleId,
            @RequestBody KindArticle tempData) {
        var kindArticle = kindArticleService.updateById(kindArticleId, tempData);
        if (kindArticle != null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "deleteAllById")
    public ResponseEntity<List<KindArticle>> deleteAllById(@RequestParam(name = "listId") List<Long> listId) {
        var kindArticleList = kindArticleService.deleteAllById(listId);
        if (kindArticleList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(kindArticleList, HttpStatus.OK);
    }
}
