package com.alcon3sl.cms.services.article.subfamily;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.alcon3sl.cms.exception.SubFamilyNotFoundException;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.article.SubFamilyRepository;
import com.alcon3sl.cms.model.util.JUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DbSubFamilyService implements SubFamilyService {
    private final SubFamilyRepository subFamilyRepository;

    @Autowired
    public DbSubFamilyService(SubFamilyRepository subFamilyRepository) {
        this.subFamilyRepository = subFamilyRepository;
    }

    @Override
    public Page<SubFamily> findAll(String filter, PageRequest pageRequest) {
        if (filter.isEmpty())
            return subFamilyRepository.findAll(pageRequest);

        List<SubFamily> subFamilyList = new ArrayList<>();
        String[] filters = filter.replaceAll("\s+", " ").split(" ");
        for (String current : filters) {
            List<SubFamily> subList = subFamilyRepository.findAllByNameOrFamilyOrSpecialty(current);
            subFamilyList = JUtil.refineList(subFamilyList, subList);
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), subFamilyList.size());

        List<SubFamily> subList = subFamilyList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, subFamilyList.size());
    }

    @Override
    public SubFamily findById(Long subfamilyId) {
        return subFamilyRepository.findById(subfamilyId).orElseThrow(
                () -> new SubFamilyNotFoundException("Subfamily not found")
        );
    }

    @Override
    public SubFamily save(SubFamily subfamily) {
        boolean flag = (subfamily == null || subfamily.getName().isEmpty() ||
        subfamily.getCode().isEmpty() || subfamily.getFamily().getId() == 0);
        if (flag)
            throw new SubFamilyNotFoundException("Wrong data");
        if (!subFamilyRepository.findByCodeIgnoreCase(subfamily.getCode().trim().toUpperCase()).isEmpty())
            throw new SubFamilyNotFoundException("The code already exists");
        if (!subFamilyRepository.findByNameIgnoreCase(subfamily.getName().trim().toUpperCase()).isEmpty())
            throw new SubFamilyNotFoundException("The name already exists");
        return subFamilyRepository.save(subfamily);
    }

    @Override
    public SubFamily deleteById(Long subfamilyId) {
        SubFamily subFamily = findById(subfamilyId);
        subFamilyRepository.deleteById(subfamilyId);
        return subFamily;
    }

    @Override
    public SubFamily updateById(Long subfamilyId, SubFamily tempData) {
        SubFamily subFamily = findById(subfamilyId);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(subFamily.getCode(), code)) {
            if (!subFamilyRepository.findByCodeIgnoreCase(code.trim().toUpperCase()).isEmpty())
                throw new SubFamilyNotFoundException("The name already exists");
            else
                subFamily.setCode(code);
        }

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(subFamily.getName(), name)) {
            if (!subFamilyRepository.findByNameIgnoreCase(name.trim().toUpperCase()).isEmpty())
                throw new SubFamilyNotFoundException("The name already exists");
            else
                subFamily.setName(name);
        }

        String description = tempData.getDescription();
        if (!Objects.equals(subFamily.getDescription(), description))
            subFamily.setDescription(description);

        Image image = tempData.getImage();
        if (!Objects.equals(subFamily.getImage(), image))
            subFamily.setImage(image);

        Family family = tempData.getFamily();
        if (family != null && !Objects.equals(subFamily.getFamily(), family))
            subFamily.setFamily(family);

        return subFamilyRepository.save(subFamily);
    }

    @Override
    public List<SubFamily> deleteAllById(List<Long> listId) {
        var subfamilyList = subFamilyRepository.findAllById(listId);
        subFamilyRepository.deleteAllById(listId);
        return subfamilyList;
    }
}
