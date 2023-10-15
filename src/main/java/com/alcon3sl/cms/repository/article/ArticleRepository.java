package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(value = "SELECT a.id, a.code_mf, a.code_sa, a.name, a.description, a.weight, a.volume, " +
            "a.subfamily_id, a.manufacturer_id, a.kind_article_id, a.provider_id, " +
            "a.initialCost, a.discount, a.margin, a.coin_id, a.image_id, s.id as sId, s.code as sCode, s.name as sName, " +
            "s.description as sDescription, s.image_id as sImage, s.family_id as sFamily, " +
            "f.id as fId, f.code as fCode, f.name as fName, f.description ad fDescription, f.image_id as fImage, " +
            "sp.id as spId, sp.code as spCode, sp.name as spName, sp.description as spDescription, sp.image_id as spImage, " +
            "m.id as mId, m.name as mName, m.image_id as mImage, k.id as kId, k.name as kName, " +
            "p.id as pId, p.name as pName, p.description as pDescription, p.cif as pCif, " +
            "p.isMaker as pIsMaker, p.address_id as pAddress, p.contact_details_id as pCDetails, " +
            "c.id as cId, c.code as cCode, c.name as cName " +
            "FROM article a " +
            "LEFT JOIN subfamily s ON (s.id = a.subfamily_id) " +
            "LEFT JOIN family f ON (f.id = s.family_id) " +
            "LEFT JOIN specialty sp ON (sp.id = family.specialty_id) " +
            "LEFT JOIN manufacturer m ON (m.id = a.manufacturer_id) " +
            "LEFT JOIN kind_article k ON (k.id = a.kind_article_id) " +
            "LEFT JOIN provider p ON (p.id = a.provider_id) " +
            "LEFT JOIN coin c ON (c.id = a.coin_id) " +
            "WHERE a.name ~* ?1 OR a.description ~* ?1 OR s.name ~* ?1 OR f.name ~* ?1 OR " +
            "sp.name ~* ?1 OR m.name ~* ?1 OR k.name ~* ?1 OR p.name ~* ?1 OR c.code ~* ?1", nativeQuery = true)
    List<Article> findAll(String filter);
}
