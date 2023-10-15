package com.alcon3sl.cms.services.article.kind_article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import com.alcon3sl.cms.repository.article.KindArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBKindArticleService implements KindArticleService {

    private final KindArticleRepository kindArticleRepository;

    @Autowired
    public DBKindArticleService(KindArticleRepository kindArticleRepository) {
        this.kindArticleRepository = kindArticleRepository;
    }

    @Override
    public Page<KindArticle> findAll(String name, Pageable pageable) {
        return null;
    }

    @Override
    public KindArticle findById(Long kindArticleId) {
        return null;
    }

    @Override
    public KindArticle save(KindArticle kindArticle) {
        return null;
    }

    @Override
    public KindArticle deleteById(Long kindArticleId) {
        return null;
    }

    @Override
    public KindArticle updateById(Long kindArticleId, KindArticle tempData) {
        return null;
    }

    @Override
    public List<KindArticle> deleteAllById(List<Long> listId) {
        return null;
    }

    @Override
    public List<KindArticle> findByName(String name) {
        return null;
    }
}
