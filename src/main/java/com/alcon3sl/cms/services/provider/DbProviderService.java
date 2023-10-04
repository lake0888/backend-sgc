package com.alcon3sl.cms.services.provider;

import com.alcon3sl.cms.model.util.address.Address;
import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.alcon3sl.cms.model.provider.Provider;
import com.alcon3sl.cms.exception.ProviderNotFoundException;
import com.alcon3sl.cms.repository.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DbProviderService implements ProviderService {
    private final ProviderRepository providerRepository;

    @Autowired
    public DbProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Page<Provider> findAll(String name, PageRequest pageRequest) {
        var providerList = providerRepository.findByName(name);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), providerList.size());

        var subList = providerList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, providerList.size());
    }

    @Override
    public Provider findById(Long providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found"));
    }

    @Override
    public Provider save(Provider provider) {
        boolean flag = provider == null || provider.getName().isEmpty();
        if (flag)
            throw new ProviderNotFoundException("Wrong data");
        if (!providerRepository.findByNameIgnoreCase(provider.getName().trim().toUpperCase()).isEmpty())
            throw new ProviderNotFoundException("The name already exists");
        if (provider.getCif() != null && !providerRepository.findByCifIgnoreCase(provider.getCif().trim().toUpperCase()).isEmpty())
            throw new ProviderNotFoundException("The cif already exists");
        return providerRepository.save(provider);
    }

    @Override
    public Provider deleteById(Long providerId) {
        var provider = this.findById(providerId);
        providerRepository.deleteById(providerId);
        return provider;
    }

    @Override
    public Provider updateById(Long providerId, Provider tempData) {
        var provider = this.findById(providerId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(provider.getName(), name)) {
            if (!providerRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new ProviderNotFoundException("The name already exists");
            else
                provider.setName(name);
        }

        String cif = tempData.getCif();
        if (cif != null && !Objects.equals(provider.getCif(), cif)) {
            if (!providerRepository.findByCifIgnoreCase(cif.trim().toUpperCase()).isEmpty())
                throw new ProviderNotFoundException("The cif already exists");
            else
                provider.setCif(cif);
        } else if (cif == null || cif.isEmpty())
            provider.setCif(cif);

        String description = tempData.getDescription();
        if (description != null && !Objects.equals(provider.getDescription(), description))
            provider.setDescription(description);

        Address address = tempData.getAddress();
        if (address != null && !Objects.equals(provider.getAddress(), address))
            provider.setAddress(address);

        ContactDetails contactDetails = tempData.getContactDetails();
        if (contactDetails != null && !Objects.equals(provider.getContactDetails(), contactDetails))
            provider.setContactDetails(contactDetails);

        return providerRepository.save(provider);
    }

    @Override
    public List<Provider> deleteAllById(List<Long> listId) {
        var providerList = providerRepository.findAllById(listId);
        providerRepository.deleteAllById(listId);
        return providerList;
    }

    @Override
    public List<Provider> findByName(String name) {
        return providerRepository.findByName(name);
    }
}
