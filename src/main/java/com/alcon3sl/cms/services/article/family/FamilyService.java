package com.alcon3sl.cms.services.article.family;

import com.alcon3sl.cms.model.article.family.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FamilyService {
    Page<Family> findAll(String filter, PageRequest pageRequest);
    Family findById(Long familyId);
    Family save(Family family);
    Family deleteById(Long familyId);
    Family updateById(Long familyId, Family tempData);
    List<Family> findAllBySpecialtyId(Long specialtyId);
    List<Family> findAllByListId(List<Long> listId);
    @Transactional
    List<Family> deleteAllByListId(List<Long> listId);
}