package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KindArticleRepository extends JpaRepository<KindArticle, Long> {
    @Query(value = "SELECT * FROM kind_article k WHERE k.name ~* ?1", nativeQuery = true)
    Page<KindArticle> findAllByName(String name, Pageable pageable);

    List<KindArticle> findAllByNameOrderByName(String name);

    List<KindArticle> findByNameIgnoreCase(String name);
}
