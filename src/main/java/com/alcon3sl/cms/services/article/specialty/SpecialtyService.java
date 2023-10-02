package com.alcon3sl.cms.services.article.specialty;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    List<Specialty> findAll(String name);
    Specialty findById(Long specialtyId);
    Specialty save(Specialty specialty);
    Specialty deleteById(Long specialtyId);
    Specialty updateById(Long specialtyId, Specialty tempData);
    List<Specialty> deleteAllById(List<Long> lisId);
    List<Specialty> findByFamily_NotNull(String name);
}
