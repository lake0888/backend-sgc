package com.alcon3sl.cms.services.article.specialty;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SpecialtyService {
    Page<Specialty> findAll(String name, PageRequest pageRequest);
    Specialty findById(Long specialtyId);
    Specialty save(Specialty specialty);
    Specialty deleteById(Long specialtyId);
    Specialty updateById(Long specialtyId, Specialty tempData);
    List<Specialty> deleteAllById(List<Long> lisId);
}
