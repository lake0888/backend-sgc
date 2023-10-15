package com.alcon3sl.cms.controller.provider;

import com.alcon3sl.cms.model.provider.Provider;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.provider.DbProviderService;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "provider")
public class ProviderController {
    private final DbProviderService providerService;

    @Autowired
    public ProviderController(DbProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping
    public ResponseEntity<Page<Provider>> findAll(@RequestParam Optional<String> filter, Pageable pageable) {
        var page = providerService.findAll(
                filter.orElse(""),
                PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSortOr(Sort.by(Sort.Direction.ASC, "name"))
                )
        );
        if (page.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = "{providerId}")
    public ResponseEntity<Provider> findById(@PathVariable(name = "providerId") Long providerId) {
        return new ResponseEntity<>(providerService.findById(providerId), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Provider> save(
            @RequestPart(name = "provider") Provider provider,
            @RequestPart(name = "imageFile") MultipartFile imageFile) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        provider.getContactDetails().setImage(image);
        return new ResponseEntity<>(providerService.save(provider), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{providerId}")
    public ResponseEntity<Provider> deleteById(@PathVariable(name = "providerId") Long providerId) {
        return new ResponseEntity<>(providerService.deleteById(providerId), HttpStatus.OK);
    }

    @PutMapping(path = "{providerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Provider> updateById(
            @PathVariable(name = "providerId") Long providerId,
            @RequestPart(name = "provider") Provider tempData,
            @RequestPart(name = "imageFile") MultipartFile imageFile
    ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        tempData.getContactDetails().setImage(image);
        return new ResponseEntity<>(providerService.updateById(providerId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Provider>> deleteAllById(
            @RequestParam(name = "listId")  List<Long> listId) {
        var providerList = providerService.deleteAllById(listId);
        if (providerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(providerList, HttpStatus.OK);
    }
}
