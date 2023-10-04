package com.alcon3sl.cms.services.util.country;

import com.alcon3sl.cms.model.util.JUtil;
import com.alcon3sl.cms.model.util.country.Country;
import com.alcon3sl.cms.exception.CountryNotFoundException;
import com.alcon3sl.cms.repository.util.country.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll(String name) {
        return countryRepository.findByName(name);
    }

    public Page<Country> findAll(String name, PageRequest pageRequest) {
        var countryList = this.findAll(name);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), countryList.size());

        var subList = countryList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, countryList.size());
    }

    @Override
    public Country findById(Long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException("Country not found"));
    }

    @Override
    public Country save(Country country) {
        boolean flag = country == null || country.getName().isEmpty() || country.getCode().isEmpty();
        if (flag)
            throw new CountryNotFoundException("Wrong data");
        if (!countryRepository.findByCodeIgnoreCase(country.getCode().trim().toUpperCase()).isEmpty())
            throw new CountryNotFoundException("The code already exists");
        if (!countryRepository.findByNameIgnoreCase(country.getName().trim().toUpperCase()).isEmpty())
            throw new CountryNotFoundException("The name already exists");
        if (!countryRepository.findByNationalityIgnoreCase(country.getNationality().trim().toUpperCase()).isEmpty())
            throw new CountryNotFoundException("The nationality already exists");
        if (!countryRepository.findByPhoneCodeIgnoreCase(country.getPhoneCode().trim().toUpperCase()).isEmpty())
            throw new CountryNotFoundException("The phone code already exists");
        return countryRepository.save(country);
    }

    @Override
    public Country deleteById(Long countryId) {
        var country = this.findById(countryId);
        countryRepository.deleteById(countryId);
        return country;
    }

    @Override
    public Country updateById(Long countryId, Country tempData) {
        var country = this.findById(countryId);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(country.getCode(), code)) {
            if (!countryRepository.findByCodeIgnoreCase(code.trim().toUpperCase()).isEmpty())
                throw new CountryNotFoundException("The code already exists");
            else
                country.setCode(code);
        }

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(country.getName(), name)) {
            if (!countryRepository.findByNameIgnoreCase(name.trim().toUpperCase()).isEmpty())
                throw new CountryNotFoundException("The name already exists");
            else
                country.setName(name);
        }

        String nationality = tempData.getNationality();
        if (nationality != null && !Objects.equals(country.getNationality(), nationality)) {
            if (!countryRepository.findByNationalityIgnoreCase(nationality.trim().toUpperCase()).isEmpty())
                throw new CountryNotFoundException("The nationality already exists");
            else
                country.setNationality(nationality);
        }

        String phoneCode = tempData.getPhoneCode();
        if (phoneCode != null && !Objects.equals(country.getPhoneCode(), phoneCode)) {
            if (!countryRepository.findByPhoneCodeIgnoreCase(phoneCode.trim().toUpperCase()).isEmpty())
                throw new CountryNotFoundException("The phone code already exists");
            else
                country.setPhoneCode(phoneCode);
        }

        return countryRepository.save(country);
    }

    @Override
    public List<Country> deleteAllById(List<Long> listId) {
        var countryList = countryRepository.findAllById(listId);
        countryRepository.deleteAllById(listId);
        return countryList;
    }

    @Override
    public Long count() {
        return countryRepository.count();
    }

    @Override
    public List<Country> findByState_NotNull(String name) {
        var countryList = this.findByState_NotNull();
        if (!countryList.isEmpty())
            countryList = JUtil.refineList(countryList, this.findAll(name));
        return countryList;
    }

    private List<Country> findByState_NotNull() {
        return countryRepository.findByStateList_NotNull();
    }


}
