package com.alcon3sl.cms.services.article.article;

import com.alcon3sl.cms.model.article.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ArticleService {
    Page<Article> findAll(String filter, PageRequest pageRequest);

    Article findById(Long articleId);

    Article save(Article article);

    Article deleteById(Long articleId);

    Article updateById(Long articleId, Article tempData);

    List<Article> deleteAllById(List<Long> listId);
}
