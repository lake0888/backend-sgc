package com.alcon3sl.cms.services.article;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.model.article.family.FamilyException;
import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.alcon3sl.cms.repository.article.FamilyRepository;
import com.alcon3sl.cms.utils.JUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FamilyService {
    private final FamilyRepository familyRepository;
    @Autowired
    public FamilyService(FamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    public Page<Family> findAll(String filter, PageRequest pageRequest) {
        if (filter.isEmpty())
            return familyRepository.findAll(pageRequest);

        List<Family> familyList = new ArrayList<>();
        String[] filters = filter.replaceAll("\s+", " ").split(" ");

        for (String current : filters) {
            List<Family> subList = familyRepository.findAllByNameOrSpecialty(current);
            familyList = JUtil.refineList(familyList, subList);
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), familyList.size());

        List<Family> subList = familyList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, familyList.size());
    }

    public Family findById(Long familyId) {
        return familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyException("Family not found"));
    }

    public Family save(Family family) {
        boolean flag = family == null || family.getName().isEmpty() ||
                family.getCode().isEmpty() || family.getSpecialty().getId() == 0;
        if (flag)
            throw new FamilyException("Wrong data");
        if (!familyRepository.findByCode(family.getCode().trim().toUpperCase()).isEmpty())
            throw new FamilyException("The code already exists");
        if (!familyRepository.findByName(family.getName().trim().toUpperCase()).isEmpty())
            throw new FamilyException("The name already exists");
        return familyRepository.save(family);
    }

    public Family deleteById(Long familyId) {
        Family family = findById(familyId);
        familyRepository.deleteById(familyId);
        return family;
    }

    public Family updateById(Long familyId, Family tempData) {
        Family family = findById(familyId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(family.getName(), name)) {
            if (!familyRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new FamilyException("The name already exists");
            else
                family.setName(name);
        }

        String description = tempData.getDescription();
        if (!Objects.equals(family.getDescription(), description))
            family.setDescription(description);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(family.getCode(), code)) {
            if (!familyRepository.findByCode(code.trim().toUpperCase()).isEmpty())
                throw new FamilyException("The code already exists");
            else
                family.setCode(code);
        }

        byte[] image = tempData.getImage();
        if (!Arrays.equals(family.getImage(), image))
            family.setImage(image);


        Specialty specialty = tempData.getSpecialty();
        if (specialty != null && !family.getSpecialty().equals(specialty))
            family.setSpecialty(specialty);

        return familyRepository.save(family);
    }

    public List<Family> findAllBySpecialtyId(Long specialtyId) {
        return familyRepository.findAllBySpecialtyId(specialtyId);
    }

    public List<Family> findAllByListId(List<Long> listId) {
        return familyRepository.findAllByListId(listId);
    }

    @Transactional
    public List<Family> deleteAllByListId(List<Long> listId) {
        var familyList = findAllByListId(listId);
        familyRepository.deleteAllByListId(listId);
        return familyList;
    }
}
