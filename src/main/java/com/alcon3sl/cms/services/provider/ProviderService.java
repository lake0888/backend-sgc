package com.alcon3sl.cms.services.provider;

import com.alcon3sl.cms.model.contact_details.address.Address;
import com.alcon3sl.cms.model.contact_details.contact_details.ContactDetails;
import com.alcon3sl.cms.model.provider.Provider;
import com.alcon3sl.cms.model.provider.ProviderException;
import com.alcon3sl.cms.repository.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Page<Provider> findAll(String filter, PageRequest pageRequest) {
        if (filter.isEmpty())
            return providerRepository.findAll(pageRequest);
        return providerRepository.findAllByName(filter, pageRequest);
    }

    public Provider findById(Long providerId) {
        return providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderException("Provider not found"));
    }

    public Provider save(Provider provider) {
        boolean flag = provider == null || provider.getName().isEmpty();
        if (flag)
            throw new ProviderException("Wrong data");
        if (!providerRepository.findByName(provider.getName().trim().toUpperCase()).isEmpty())
            throw new ProviderException("The name already exists");
        if (!providerRepository.findByCif(provider.getCif().trim().toUpperCase()).isEmpty())
            throw new ProviderException("The cif already exists");
        return providerRepository.save(provider);
    }

    public Provider deleteById(Long providerId) {
        var provider = this.findById(providerId);
        providerRepository.deleteById(providerId);
        return provider;
    }

    public Provider updateById(Long providerId, Provider tempData) {
        var provider = this.findById(providerId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(provider.getName(), name)) {
            if (!providerRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new ProviderException("The name already exists");
            else
                provider.setName(name);
        }

        String description = tempData.getDescription();
        if (description != null && !Objects.equals(provider.getDescription(), description))
            provider.setDescription(description);

        String cif = tempData.getCif();
        if (cif != null && !Objects.equals(provider.getCif(), cif)) {
            if (!providerRepository.findByCif(cif.trim().toUpperCase()).isEmpty())
                throw new ProviderException("The cif already exists");
            else
                provider.setCif(cif);
        }

        Address address = tempData.getAddress();
        if (address != null && !Objects.equals(provider.getAddress(), address))
            provider.setAddress(address);

        ContactDetails contactDetails = tempData.getContactDetails();
        if (contactDetails != null && !Objects.equals(provider.getContactDetails(), contactDetails))
            provider.setContactDetails(contactDetails);

        return providerRepository.save(provider);
    }

    public List<Provider> findAllByListId(List<Long> listId) {
        return providerRepository.findAllByListId(listId);
    }

    @Transactional
    public List<Provider> deleteAllByListId(List<Long> listId) {
        var providerList = this.findAllByListId(listId);
        providerRepository.deleteAllByListId(listId);
        return providerList;
    }
}
