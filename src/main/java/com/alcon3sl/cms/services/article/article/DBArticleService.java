package com.alcon3sl.cms.services.article.article;

import com.alcon3sl.cms.exception.ArticleNotFoundException;
import com.alcon3sl.cms.model.article.article.Article;
import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.alcon3sl.cms.model.provider.Provider;
import com.alcon3sl.cms.model.util.JUtil;
import com.alcon3sl.cms.model.util.coin.Coin;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DBArticleService implements ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public DBArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> findAll(String filter, PageRequest pageRequest) {
        List<Article> articleList = new ArrayList<>();

        String[] filters = filter.replaceAll("\s+", " ").split(" ");
        for (String current : filters) {
            List<Article> subList = articleRepository.findAll(current);
            articleList = JUtil.refineList(articleList, subList);
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), articleList.size());

        var subList = articleList.subList(start, end);

        return new PageImpl<>(subList, pageRequest, articleList.size());
    }

    @Override
    public Article findById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

    @Override
    public Article save(Article article) {
        boolean flag = (article == null || article.getName().isEmpty() ||  article.getProvider() == null);
        if (flag)
            throw new IllegalArgumentException("Wrong data");

        return articleRepository.save(article);
    }

    @Override
    public Article deleteById(Long articleId) {
        var article = this.findById(articleId);
        articleRepository.deleteById(articleId);
        return article;
    }

    @Override
    public Article updateById(Long articleId, Article tempData) {
        var article = this.findById(articleId);

        String code_mf = tempData.getCode_mf();
        if (code_mf != null && !code_mf.isEmpty() && !Objects.equals(article.getCode_mf(), code_mf))
            article.setCode_mf(code_mf);

        String code_sa = tempData.getCode_sa();
        if (code_sa != null && !code_sa.isEmpty() && !Objects.equals(article.getCode_sa(), code_sa))
            article.setCode_sa(code_sa);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(article.getName(), name))
            article.setName(name);

        String description = tempData.getDescription();
        if (description != null && !description.isEmpty() && !Objects.equals(article.getDescription(), description))
            article.setDescription(description);

        double weight = tempData.getWeight();
        if (Double.compare(article.getWeight(), weight) != 0)
            article.setWeight(weight);

        double volume = tempData.getVolume();
        if (Double.compare(article.getVolume(), volume) != 0)
            article.setVolume(volume);

        SubFamily subfamily = tempData.getSubfamily();
        if (subfamily != null && !Objects.equals(article.getSubfamily(), subfamily))
            article.setSubfamily(subfamily);

        Manufacturer manufacturer = tempData.getManufacturer();
        if (manufacturer != null && !Objects.equals(article.getManufacturer(), manufacturer))
            article.setManufacturer(manufacturer);

        KindArticle kindArticle = tempData.getKindArticle();
        if (kindArticle != null && !Objects.equals(article.getKindArticle(), kindArticle))
            article.setKindArticle(kindArticle);

        Provider provider = tempData.getProvider();
        if (provider != null && !Objects.equals(article.getProvider(), provider))
            article.setProvider(provider);

        double initialCost = tempData.getInitialCost();
        if (Double.compare(article.getInitialCost(), initialCost) != 0)
            article.setInitialCost(initialCost);

        double discount = tempData.getDiscount();
        if (Double.compare(article.getDiscount(), discount) != 0)
            article.setDiscount(discount);

        double margin = tempData.getMargin();
        if (Double.compare(article.getMargin(), margin) != 0)
            article.setMargin(margin);

        Coin coin = tempData.getCoin();
        if (coin != null && !Objects.equals(article.getCoin(), coin))
            article.setCoin(coin);

        Image image = tempData.getImage();
        if (image != null && !Objects.equals(article.getImage(), image))
            article.setImage(image);

        return articleRepository.save(article);
    }

    @Override
    public List<Article> deleteAllById(List<Long> listId) {
       var articleList = articleRepository.findAllById(listId);
       articleRepository.deleteAllById(listId);
       return articleList;
    }
}
