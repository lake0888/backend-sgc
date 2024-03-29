package com.alcon3sl.cms.services.article.kind_article;

import com.alcon3sl.cms.exception.KindArticleNotFoundException;
import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import com.alcon3sl.cms.repository.article.KindArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DBKindArticleService implements KindArticleService {

    private final KindArticleRepository kindArticleRepository;

    @Autowired
    public DBKindArticleService(KindArticleRepository kindArticleRepository) {
        this.kindArticleRepository = kindArticleRepository;
    }

    @Override
    public Page<KindArticle> findAll(String name, Pageable pageable) {
        return kindArticleRepository.findAllByName(name, pageable);
    }

    @Override
    public KindArticle findById(Long kindArticleId) {
        return kindArticleRepository
                .findById(kindArticleId)
                .orElseThrow(() -> new KindArticleNotFoundException("Kind article not found"));
    }

    @Override
    public URI save(KindArticle newKindArticle, UriComponentsBuilder ucb) {
        boolean flag = (newKindArticle == null || newKindArticle.getName().isEmpty());
        if (flag)
            throw new IllegalArgumentException("Wrong data");
        else if (!kindArticleRepository.findByNameIgnoreCase(newKindArticle.getName()).isEmpty())
            throw new IllegalArgumentException("The name already exists");

        var kindArticle = kindArticleRepository.save(newKindArticle);
        return ucb
                .path("kind_article/{id}")
                .buildAndExpand(kindArticle.getId())
                .toUri();
    }

    @Override
    public KindArticle deleteById(Long kindArticleId) {
        var kindArticle = this.findById(kindArticleId);
        kindArticleRepository.deleteById(kindArticleId);
        return kindArticle;
    }

    @Override
    public KindArticle updateById(Long kindArticleId, KindArticle tempData) {
        KindArticle kindArticle = this.findById(kindArticleId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(kindArticle.getName(), name)) {
            if (!kindArticleRepository.findByNameIgnoreCase(name).isEmpty())
                throw new IllegalArgumentException("The name already exists");
            else
                kindArticle.setName(name);
        }

        return kindArticleRepository.save(kindArticle);
    }

    @Override
    public List<KindArticle> deleteAllById(List<Long> listId) {
        var kindArticleList = kindArticleRepository.findAllById(listId);
        kindArticleRepository.deleteAllById(listId);
        return kindArticleList;
    }

    @Override
    public List<KindArticle> findByName(String name) {
        return kindArticleRepository.findAllByNameOrderByName(name);
    }
}
