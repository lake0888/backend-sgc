package com.alcon3sl.cms.services.contact_details.country;

import com.alcon3sl.cms.model.contact_details.country.country.Country;
import com.alcon3sl.cms.model.contact_details.country.countrystate.CountryState;
import com.alcon3sl.cms.model.contact_details.country.countrystate.CountryStateException;
import com.alcon3sl.cms.repository.contact_details.country.CountryStateRepository;
import com.alcon3sl.cms.utils.JUtil;
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
public class CountryStateService {
    private final CountryStateRepository countryStateRepository;
    @Autowired
    public CountryStateService(CountryStateRepository countryStateRepository) {
        this.countryStateRepository = countryStateRepository;
    }

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

    public CountryState findById(Long countryStateId) {
        return countryStateRepository.findById(countryStateId)
                .orElseThrow(() -> new CountryStateException("Country State not found"));
    }

    public CountryState save(CountryState countryState) {
        boolean flag = countryState == null || countryState.getName().isEmpty()
                || countryState.getCountry() == null || countryState.getCountry().getCode().isEmpty() ||
                countryState.getCountry().getName().isEmpty();
        if (flag)
            throw new CountryStateException("Wrong data");
        if (!countryStateRepository.findByName(countryState.getName().trim().toUpperCase()).isEmpty())
            throw new CountryStateException("The name already exists");
        return countryStateRepository.save(countryState);
    }

    public CountryState deleteById(Long countryStateId) {
        var countryState = this.findById(countryStateId);
        countryStateRepository.deleteById(countryStateId);
        return countryState;
    }

    public CountryState updateById(Long countryStateId, CountryState tempData) {
        var countryState = this.findById(countryStateId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(countryState.getName(), name)) {
            if (!countryStateRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new CountryStateException("The name already exists");
            else
                countryState.setName(name);
        }

        Country country = tempData.getCountry();
        if (country != null && !Objects.equals(countryState.getCountry(), country))
            countryState.setCountry(country);

        return countryStateRepository.save(countryState);
    }

    public List<CountryState> findAllByListId(List<Long> listId) {
        return countryStateRepository.findAllByListId(listId);
    }

    @Transactional
    public List<CountryState> deleteAllByListId(List<Long> listId) {
        var countryStateList = this.findAllByListId(listId);
        countryStateRepository.deleteAllByListId(listId);
        return countryStateList;
    }
}
