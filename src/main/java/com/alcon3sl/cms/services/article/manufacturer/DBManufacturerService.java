package com.alcon3sl.cms.services.article.manufacturer;

import com.alcon3sl.cms.exception.ManufacturerNotFoundException;
import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.article.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DBManufacturerService implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public DBManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Page<Manufacturer> findAll(String name, Pageable pageable) {
        return manufacturerRepository.findAllByName(name, pageable);
    }

    @Override
    public Manufacturer findById(Long manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException("Manufacturer not found"));
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        boolean flag = (manufacturer == null || manufacturer.getName().isEmpty());
        if (flag)
            throw new IllegalArgumentException("Wrong data");
        else if (!manufacturerRepository.findByNameIgnoreCase(manufacturer.getName()).isEmpty())
            throw new IllegalArgumentException("The name already exists");

        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer deleteById(Long manufacturerId) {
        var manufacturer = this.findById(manufacturerId);
        manufacturerRepository.deleteById(manufacturerId);
        return manufacturer;
    }

    @Override
    public Manufacturer updateById(Long manufacturerId, Manufacturer tempData) {
        var manufacturer = this.findById(manufacturerId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(manufacturer.getName(), name)) {
            if (!manufacturerRepository.findByNameIgnoreCase(name).isEmpty())
                throw new IllegalArgumentException("The name already exists");
            else
                manufacturer.setName(name);
        }

        Image image = tempData.getImage();
        if (image != null && !Objects.equals(manufacturer.getImage(), image)) {
            manufacturer.setImage(image);
        }

        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> deleteAllById(List<Long> listId) {
        var manufacturerList = manufacturerRepository.findAllById(listId);
        manufacturerRepository.deleteAllById(listId);
        return manufacturerList;
    }

    @Override
    public List<Manufacturer> findByName(String name) {
        return manufacturerRepository.findAllByNameOrderByName(name);
    }
}
