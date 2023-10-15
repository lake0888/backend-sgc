package com.alcon3sl.cms.services.article.manufacturer;

import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import com.alcon3sl.cms.repository.article.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBManufacturerService implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public DBManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Page<Manufacturer> findAll(String name, Pageable pageable) {
        return null;
    }

    @Override
    public Manufacturer findById(Long manufacturerId) {
        return null;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Manufacturer deleteById(Long manufacturerId) {
        return null;
    }

    @Override
    public Manufacturer updateById(Long manufacturerId, Manufacturer tempData) {
        return null;
    }

    @Override
    public List<Manufacturer> deleteAllById(List<Long> listId) {
        return null;
    }

    @Override
    public List<Manufacturer> findByName(String name) {
        return null;
    }
}
