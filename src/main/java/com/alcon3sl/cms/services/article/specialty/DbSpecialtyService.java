package com.alcon3sl.cms.services.article.specialty;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.alcon3sl.cms.exception.SpecialtyNotFoundException;
import com.alcon3sl.cms.model.util.JUtil;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.article.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

@Service
public class DbSpecialtyService implements SpecialtyService{

    private final SpecialtyRepository specialtyRepository;
    @Autowired
    public DbSpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public List<Specialty> findAll(String name) {
        return specialtyRepository.findAll(name)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found"));
    }

    public Page<Specialty> convertToPage(String name, PageRequest pageRequest) {
        var specialtyList = this.findAll(name);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), specialtyList.size());

        var subList = specialtyList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, specialtyList.size());
    }

    @Override
    public Specialty findById(Long specialtyId) {
        return specialtyRepository.findById(specialtyId).
                orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found"));
    }

    @Override
    public Specialty save(Specialty specialty) {
        boolean flag = specialty == null || specialty.getName().isEmpty() || specialty.getCode().isEmpty();
        if (flag)
            throw new IllegalStateException("Wrong data");
        if (!specialtyRepository.findByCodeIgnoreCase(specialty.getCode().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The code already exits");
        if (!specialtyRepository.findByNameIgnoreCase(specialty.getName().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The name already exits");
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty deleteById(Long specialtyId) {
        Specialty specialty = findById(specialtyId);
        specialtyRepository.deleteById(specialtyId);
        return specialty;
    }

    @Override
    public Specialty updateById(Long specialtyId, Specialty tempData) {
        Specialty specialty = findById(specialtyId);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(specialty.getCode(), code)) {
            if (!specialtyRepository.findByCodeIgnoreCase(code.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The code already exists");
            else
                specialty.setCode(code);
        }

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(specialty.getName(), name)) {
            if (!specialtyRepository.findByNameIgnoreCase(name.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The name already exists");
            else
                specialty.setName(name);
        }

        String description = tempData.getDescription();
        if (!Objects.equals(specialty.getDescription(), description))
            specialty.setDescription(description);

        Image image = tempData.getImage();
        if (!Objects.equals(specialty.getImage(), image))
            specialty.setImage(image);

        return specialtyRepository.save(specialty);
    }

    @Override
    public List<Specialty> deleteAllById(List<Long> listId) {
        var specialtyList = specialtyRepository.findAllById(listId);
        specialtyRepository.deleteAllById(listId);
        return specialtyList;
    }

    @Override
    public List<Specialty> findByFamily_NotNull(String name) {
        var specialtyList = this.findByFamily_NotNull();
        if (!specialtyList.isEmpty())
            specialtyList = JUtil.refineList(specialtyList, this.findAll(name));
        return specialtyList;
    }

    private List<Specialty> findByFamily_NotNull() {
        return specialtyRepository.findByFamilies_NotNull();
    }
}
