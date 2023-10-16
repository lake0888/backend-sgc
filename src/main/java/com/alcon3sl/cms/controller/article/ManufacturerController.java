package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.article.manufacturer.DBManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "manufacturer")
public class ManufacturerController {
    private final DBManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(DBManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public ResponseEntity<Page<Manufacturer>> findAll(
            @RequestParam Optional<String> name, Pageable pageable
    ) {
        var page = manufacturerService.findAll(name.orElse(""),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
                ));
        if (page.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = "{manufacturerId}")
    public ResponseEntity<Manufacturer> findById(@PathVariable(name = "manufacturerId") Long manufacturerId) {
        var manufacturer = manufacturerService.findById(manufacturerId);
        if (manufacturer != null)
            return new ResponseEntity<>(manufacturer, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<URI> save(
            @RequestPart(name = "manufacturer") Manufacturer manufacturer,
            @RequestPart(name = "imageFile") MultipartFile imageFile,
            UriComponentsBuilder ucb) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        manufacturer.setImage(image);
        return new ResponseEntity<>(manufacturerService.save(manufacturer, ucb), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{manufacturerId}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "manufacturerId") Long manufacturerId) {
        var manufacturer = manufacturerService.deleteById(manufacturerId);
        if (manufacturer != null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "{manufacturerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateById(
            @PathVariable(name = "manufacturerId") Long manufacturerId,
            @RequestPart(name = "manufacturer") Manufacturer updateManufacturer,
            @RequestPart(name = "imageFile") MultipartFile imageFile
            ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        updateManufacturer.setImage(image);
        var manufacturer = manufacturerService.updateById(manufacturerId, updateManufacturer);
        if (manufacturer != null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "deleteAllById")
    public ResponseEntity<List<Manufacturer>> deleteAllById(@RequestParam(name = "listId") List<Long> listId) {
        var manufacturerList = manufacturerService.deleteAllById(listId);
        if (manufacturerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(manufacturerList, HttpStatus.OK);
    }
}
