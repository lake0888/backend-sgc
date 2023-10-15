package com.alcon3sl.cms.services.article.kind_article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KindArticleService {
    Page<KindArticle> findAll(String name, Pageable pageable);

    KindArticle findById(Long kindArticleId);

    KindArticle save(KindArticle kindArticle);

    KindArticle deleteById(Long kindArticleId);

    KindArticle updateById(Long kindArticleId, KindArticle tempData);

    List<KindArticle> deleteAllById(List<Long> listId);

    List<KindArticle> findByName(String name);
}
