package com.alcon3sl.cms.services.util.country_state;

import com.alcon3sl.cms.model.util.country.Country;
import com.alcon3sl.cms.model.util.countrystate.CountryState;
import com.alcon3sl.cms.exception.CountryStateNotFoundException;
import com.alcon3sl.cms.repository.util.country_state.CountryStateRepository;
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
public class DbCountryStateService implements CountryStateService {
    private final CountryStateRepository countryStateRepository;
    @Autowired
    public DbCountryStateService(CountryStateRepository countryStateRepository) {
        this.countryStateRepository = countryStateRepository;
    }

    @Override
    public Page<CountryState> findAll(String filter, PageRequest pageRequest) {
        if (filter.isEmpty())
            return countryStateRepository.findAll(pageRequest);

        List<CountryState> countryStateList = new ArrayList<>();
        String[] filters = filter.replaceAll("\s+", " ").split(" ");

        for (String current : filters) {
            var subList = countryStateRepository.findAllByNameOrCountry(current);
            countryStateList = JUtil.refineList(countryStateList, subList);
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), countryStateList.size());

        List<CountryState> subList = countryStateList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, countryStateList.size());
    }

    @Override
    public CountryState findById(Long countryStateId) {
        return countryStateRepository.findById(countryStateId)
                .orElseThrow(() -> new CountryStateNotFoundException("Country State not found"));
    }

    @Override
    public CountryState save(CountryState countryState) {
        boolean flag = countryState == null || countryState.getName().isEmpty()
                || countryState.getCountry() == null || countryState.getCountry().getCode().isEmpty() ||
                countryState.getCountry().getName().isEmpty();
        if (flag)
            throw new CountryStateNotFoundException("Wrong data");
        return countryStateRepository.save(countryState);
    }

    @Override
    public CountryState deleteById(Long countryStateId) {
        var countryState = this.findById(countryStateId);
        countryStateRepository.deleteById(countryStateId);
        return countryState;
    }

    @Override
    public CountryState updateById(Long countryStateId, CountryState tempData) {
        var countryState = this.findById(countryStateId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(countryState.getName(), name)) {
            countryState.setName(name);
        }

        Country country = tempData.getCountry();
        if (country != null && !Objects.equals(countryState.getCountry(), country))
            countryState.setCountry(country);

        return countryStateRepository.save(countryState);
    }

    @Override
    public List<CountryState> deleteAllById(List<Long> listId) {
        var countryStateList = countryStateRepository.findAllById(listId);
        countryStateRepository.deleteAllById(listId);
        return countryStateList;
    }

    public List<CountryState> findAllByCountryCode(String code) {
        return countryStateRepository.findAllByCountryCode(code);
    }
}
