package com.alcon3sl.cms.services.contact_details.country;

import com.alcon3sl.cms.model.contact_details.country.country.Country;
import com.alcon3sl.cms.model.contact_details.country.country.CountryException;
import com.alcon3sl.cms.repository.contact_details.country.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Page<Country> findAll(String filter, PageRequest pageRequest) {
        if (filter.isEmpty())
            return countryRepository.findAll(pageRequest);
        return countryRepository.findAllByCodeOrName(filter, pageRequest);
    }

    public Country findById(Long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryException("Country not found"));
    }

    public Country save(Country country) {
        boolean flag = country == null || country.getName().isEmpty() || country.getCode().isEmpty();
        if (flag)
            throw new CountryException("Wrong data");
        if (!countryRepository.findByCode(country.getCode().trim().toUpperCase()).isEmpty())
            throw new CountryException("The code already exists");
        if (!countryRepository.findByName(country.getName().trim().toUpperCase()).isEmpty())
            throw new CountryException("The name already exists");
        if (!countryRepository.findByName(country.getNationality().trim().toUpperCase()).isEmpty())
            throw new CountryException("The nationality already exists");
        if (!countryRepository.findByName(country.getPhoneCode().trim().toUpperCase()).isEmpty())
            throw new CountryException("The phone code already exists");
        return countryRepository.save(country);
    }

    public Country deleteById(Long countryId) {
        var country = this.findById(countryId);
        countryRepository.deleteById(countryId);
        return country;
    }

    public Country updateById(Long countryId, Country tempData) {
        var country = this.findById(countryId);

        String code = tempData.getCode();
        if (code != null && !code.isEmpty() && !Objects.equals(country.getCode(), code)) {
            if (!countryRepository.findByCode(code.trim().toUpperCase()).isEmpty())
                throw new CountryException("The code already exists");
            else
                country.setCode(code);
        }

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(country.getName(), name)) {
            if (!countryRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new CountryException("The name already exists");
            else
                country.setName(name);
        }

        String nationality = tempData.getNationality();
        if (nationality != null && !Objects.equals(country.getNationality(), nationality)) {
            if (!countryRepository.findByNationality(nationality.trim().toUpperCase()).isEmpty())
                throw new CountryException("The nationality already exists");
            else
                country.setNationality(nationality);
        }

        String phoneCode = tempData.getPhoneCode();
        if (phoneCode != null && !Objects.equals(country.getPhoneCode(), phoneCode)) {
            if (!countryRepository.findByPhoneCode(phoneCode.trim().toUpperCase()).isEmpty())
                throw new CountryException("The phone code already exists");
            else
                country.setPhoneCode(phoneCode);
        }

        return countryRepository.save(country);
    }

    public List<Country> findAllByListId(List<Long> listId) {
        return countryRepository.findAllByListId(listId);
    }

    @Transactional
    public List<Country> deleteAllByListId(List<Long> listId) {
        var countryList = findAllByListId(listId);
        countryRepository.deleteAllByListId(listId);
        return countryList;
    }
}
