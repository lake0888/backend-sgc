package com.alcon3sl.cms.services.importer;

import com.alcon3sl.cms.model.util.address.Address;
import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.alcon3sl.cms.model.importer.Importer;
import com.alcon3sl.cms.exception.ImporterNotFoundException;
import com.alcon3sl.cms.repository.importer.ImporterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DbImporterService implements ImporterService {
    private final ImporterRepository importerRepository;
    public DbImporterService(ImporterRepository importerRepository) {
        this.importerRepository = importerRepository;
    }

    @Override
    public List<Importer> findAll(String name) {
        return importerRepository.findAllByName(name);
    }
    public Page<Importer> findAll(String name, PageRequest pageRequest) {
        var importerList = this.findAll(name);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), importerList.size());

        var subList = importerList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, importerList.size());
    }

    @Override
    public Importer findById(Long importerId) {
        return importerRepository.findById(importerId)
                .orElseThrow(() -> new ImporterNotFoundException("Importer not found"));
    }

    @Override
    public Importer save(Importer importer) {
        boolean flag = (importer == null || importer.getName().isEmpty());
        if (flag)
            throw new ImporterNotFoundException("Wrong data");
        if (!importerRepository.findByName(importer.getName().trim().toUpperCase()).isEmpty())
            throw new ImporterNotFoundException("The name already exists");
        if (importer.getNit() != null && !importer.getNit().isEmpty() && !importerRepository.findByNit(importer.getNit().trim().toUpperCase()).isEmpty())
            throw new ImporterNotFoundException("The nit already exists");
        return importerRepository.save(importer);
    }

    @Override
    public Importer deleteById(Long importerId) {
        var importer = this.findById(importerId);
        importerRepository.deleteById(importerId);
        return importer;
    }

    @Override
    public Importer updateById(Long importerId, Importer tempData) {
        var importer = this.findById(importerId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(importer.getName(), name)) {
            if (!importerRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new ImporterNotFoundException("The name already exists");
            else
                importer.setName(name);
        }

        String nit = tempData.getNit();
        if (nit != null && !nit.isEmpty() && !Objects.equals(importer.getNit(), nit)) {
            if (!importerRepository.findByNit(nit.trim().toUpperCase()).isEmpty())
                throw new ImporterNotFoundException("The nit already exists");
            else
                importer.setNit(nit);
        } else if (nit == null || nit.isEmpty()) {
                importer.setNit(nit);
        }

        String description = tempData.getDescription();
        if (description != null && !description.isEmpty() && !Objects.equals(importer.getDescription(), description))
            importer.setDescription(description);

        Address address = tempData.getAddress();
        if (address != null && !Objects.equals(importer.getAddress(), address))
            importer.setAddress(address);

        ContactDetails contactDetails = tempData.getContactDetails();
        if (contactDetails != null && !Objects.equals(importer.getContactDetails(), contactDetails))
            importer.setContactDetails(contactDetails);

        return importerRepository.save(importer);
    }

    @Override
    public List<Importer> deleteAllById(List<Long> listId) {
        var importerList = importerRepository.findAllById(listId);
        importerRepository.deleteAllById(listId);
        return importerList;
    }
}
