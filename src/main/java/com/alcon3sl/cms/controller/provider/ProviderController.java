package com.alcon3sl.cms.controller.provider;

import com.alcon3sl.cms.model.provider.Provider;
import com.alcon3sl.cms.services.provider.DbProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<Provider>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var providerList = providerService.findAll(filter.orElse(""), PageRequest.of(
                page.orElse(0),
                size.orElse(50),
                Sort.Direction.ASC, sortBy.orElse("name")
        ));
        if (providerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(providerList, HttpStatus.OK);
    }

    @GetMapping(path = "{providerId}")
    public ResponseEntity<Provider> findById(@PathVariable(name = "providerId") Long providerId) {
        return new ResponseEntity<>(providerService.findById(providerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Provider> save(@RequestBody Provider provider) {
        return new ResponseEntity<>(providerService.save(provider), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{providerId}")
    public ResponseEntity<Provider> deleteById(@PathVariable(name = "providerId") Long providerId) {
        return new ResponseEntity<>(providerService.deleteById(providerId), HttpStatus.OK);
    }

    @PutMapping(path = "{providerId}")
    public ResponseEntity<Provider> updateById(
            @PathVariable(name = "providerId") Long providerId,
            @RequestBody Provider tempData
    ) {
        return new ResponseEntity<>(providerService.updateById(providerId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<Provider>> findAllByListId(List<Long> listId) {
        var providerList = providerService.findAllByListId(listId);
        if (providerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(providerList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<Provider>> deleteAllByListId(List<Long> listId) {
        var providerList = providerService.deleteAllByListId(listId);
        if (providerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(providerList, HttpStatus.OK);
    }
}
