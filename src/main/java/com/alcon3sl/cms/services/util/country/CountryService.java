package com.alcon3sl.cms.services.util.country;

import com.alcon3sl.cms.model.util.country.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CountryService {
    List<Country> findAll(String name);

    Country findById(Long countryId);

    Country save(Country country);

    Country deleteById(Long countryId);

    Country updateById(Long countryId, Country tempData);

    List<Country> deleteAllById(List<Long> listId);

    Long count();

    List<Country> findByState_NotNull(String name);
}
