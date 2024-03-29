package com.alcon3sl.cms.services.article.manufacturer;

import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public interface ManufacturerService {
    Page<Manufacturer> findAll(String name, Pageable pageable);

    Manufacturer findById(Long manufacturerId);

    URI save(Manufacturer manufacturer, UriComponentsBuilder ucb);

    Manufacturer deleteById(Long manufacturerId);

    Manufacturer updateById(Long manufacturerId, Manufacturer tempData);

    List<Manufacturer> deleteAllById(List<Long> listId);

    List<Manufacturer> findByName(String name);
}
