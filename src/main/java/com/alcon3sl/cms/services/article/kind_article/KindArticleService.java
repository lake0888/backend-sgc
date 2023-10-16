package com.alcon3sl.cms.services.article.kind_article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface KindArticleService {
    Page<KindArticle> findAll(String name, Pageable pageable);

    KindArticle findById(Long kindArticleId);

    URI save(KindArticle kindArticle, UriComponentsBuilder ucb);

    KindArticle deleteById(Long kindArticleId);

    KindArticle updateById(Long kindArticleId, KindArticle tempData);

    List<KindArticle> deleteAllById(List<Long> listId);

    List<KindArticle> findByName(String name);
}
