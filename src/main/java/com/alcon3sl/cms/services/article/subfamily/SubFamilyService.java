package com.alcon3sl.cms.services.article.subfamily;

import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubFamilyService {
    Page<SubFamily> findAll(String filter, PageRequest pageRequest);

    SubFamily findById(Long subfamilyId);

    SubFamily save(SubFamily subfamily);

    SubFamily deleteById(Long subfamilyId);

    SubFamily updateById(Long subfamilyId, SubFamily tempData);

    List<SubFamily> findAllByListId(List<Long> listId);

    @Transactional
    List<SubFamily> deleteAllByListId(List<Long> listId);
}
