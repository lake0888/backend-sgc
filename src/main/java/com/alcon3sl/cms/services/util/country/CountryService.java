package com.alcon3sl.cms.services.util.country;

import com.alcon3sl.cms.model.util.country.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CountryService {
    Page<Country> findAll(String filter, PageRequest pageRequest);

    Country findById(Long countryId);

    Country save(Country country);

    Country deleteById(Long countryId);

    Country updateById(Long countryId, Country tempData);

    List<Country> deleteAllById(List<Long> listId);

    List<Country> findAllByName(String name);
}
