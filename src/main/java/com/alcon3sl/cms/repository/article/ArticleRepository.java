package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
