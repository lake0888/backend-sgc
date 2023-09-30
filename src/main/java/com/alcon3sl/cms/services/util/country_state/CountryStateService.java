package com.alcon3sl.cms.services.util.country_state;

import com.alcon3sl.cms.model.util.countrystate.CountryState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CountryStateService {
    Page<CountryState> findAll(String filter, PageRequest pageRequest);

    CountryState findById(Long countryStateId);

    CountryState save(CountryState countryState);

    CountryState deleteById(Long countryStateId);

    CountryState updateById(Long countryStateId, CountryState tempData);

    List<CountryState> deleteAllById(List<Long> listId);
}
