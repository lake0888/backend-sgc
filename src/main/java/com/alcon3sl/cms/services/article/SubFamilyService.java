package com.alcon3sl.cms.services.article;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.alcon3sl.cms.model.article.subfamily.SubFamilyException;
import com.alcon3sl.cms.repository.article.SubFamilyRepository;
import com.alcon3sl.cms.utils.JUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SubFamilyService {
    private final SubFamilyRepository subFamilyRepository;

    @Autowired
    public SubFamilyService(SubFamilyRepository subFamilyRepository) {
        this.subFamilyRepository = subFamilyRepository;
    }

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

    public SubFamily findById(Long subfamilyId) {
        return subFamilyRepository.findById(subfamilyId).orElseThrow(
                () -> new SubFamilyException("Subfamily not found")
        );
    }

    public SubFamily save(SubFamily subfamily) {
        boolean flag = (subfamily == null || subfamily.getName().isEmpty() ||
        subfamily.getCode().isEmpty() || subfamily.getFamily().getId() == 0);
        if (flag)
            throw new SubFamilyException("Wrong data");
        if (!subFamilyRepository.findByCode(subfamily.getCode().trim().toUpperCase()).isEmpty())
            throw new SubFamilyException("The code already exists");
        if (!subFamilyRepository.findByName(subfamily.getName().trim().toUpperCase()).isEmpty())
            throw new SubFamilyException("The name already exists");
        return subFamilyRepository.save(subfamily);
    }

    public SubFamily deleteById(Long subfamilyId) {
        SubFamily subFamily = findById(subfamilyId);
        subFamilyRepository.deleteById(subfamilyId);
        return subFamily;
    }

    public SubFamily updateById(Long subfamilyId, SubFamily tempData) {
        SubFamily subFamily = findById(subfamilyId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(subFamily.getName(), name)) {
            if (!subFamilyRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new SubFamilyException("The name already exists");
            else
                subFamily.setName(name);
        }

        String description = tempData.getDescription();
        if (!Objects.equals(subFamily.getDescription(), description))
            subFamily.setDescription(description);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(subFamily.getCode(), code)) {
            if (!subFamilyRepository.findByCode(code.trim().toUpperCase()).isEmpty())
                throw new SubFamilyException("The name already exists");
            else
                subFamily.setCode(code);
        }

        byte[] image = tempData.getImage();
        if (!Arrays.equals(subFamily.getImage(), image))
            subFamily.setImage(image);

        Family family = tempData.getFamily();
        if (family != null && !Objects.equals(subFamily.getFamily(), family))
            subFamily.setFamily(family);

        return subFamilyRepository.save(subFamily);
    }

    public List<SubFamily> findAllByListId(List<Long> listId) {
        return subFamilyRepository.findAllByListId(listId);
    }
    @Transactional
    public List<SubFamily> deleteAllByListId(List<Long> listId) {
        var subfamilyList = findAllByListId(listId);
        subFamilyRepository.deleteAllByListId(listId);
        return subfamilyList;
    }
}
