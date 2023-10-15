package com.alcon3sl.cms.services.article.article;

import com.alcon3sl.cms.model.article.article.Article;
import com.alcon3sl.cms.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBArticleService implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public DBArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> findAll(String filter, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Article findById(Long articleId) {
        return null;
    }

    @Override
    public Article save(Article article) {
        return null;
    }

    @Override
    public Article deleteById(Long articleId) {
        return null;
    }

    @Override
    public Article updateById(Long articleId, Article tempData) {
        return null;
    }

    @Override
    public List<Article> deleteAllById(List<Long> listId) {
        return null;
    }
}
