package com.alcon3sl.cms.services.importer;

import com.alcon3sl.cms.model.importer.Importer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ImporterService {
    List<Importer> findAll(String name);

    Importer findById(Long importerId);

    Importer save(Importer importer);

    Importer deleteById(Long importerId);

    Importer updateById(Long importerId, Importer tempData);

    List<Importer> deleteAllById(List<Long> listId);

    //List<Importer> findByName_NotNull();
}
