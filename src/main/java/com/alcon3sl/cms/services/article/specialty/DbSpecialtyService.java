package com.alcon3sl.cms.services.article.specialty;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.alcon3sl.cms.exception.SpecialtyNotFoundException;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.article.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DbSpecialtyService implements SpecialtyService{

    private final SpecialtyRepository specialtyRepository;
    @Autowired
    public DbSpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Page<Specialty> findAll(String name, PageRequest pageRequest) {
        if (name.isEmpty())
            return specialtyRepository.findAll(pageRequest);
        return specialtyRepository.findAllByName(name, pageRequest);
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
        if (!specialtyRepository.findByCode(specialty.getCode().trim().toUpperCase()).isEmpty())
            throw new IllegalArgumentException("The code already exits");
        if (!specialtyRepository.findByName(specialty.getName().trim().toUpperCase()).isEmpty())
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

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(specialty.getName(), name)) {
            if (!specialtyRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The code already exists");
            else
                specialty.setName(name);
        }

        String description = tempData.getDescription();
        if (!Objects.equals(specialty.getDescription(), description))
            specialty.setDescription(description);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(specialty.getCode(), code)) {
            if (!specialtyRepository.findByCode(code.trim().toUpperCase()).isEmpty())
                throw new IllegalArgumentException("The code already exists");
            else
                specialty.setCode(code);
        }

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
}
