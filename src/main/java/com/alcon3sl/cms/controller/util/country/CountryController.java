package com.alcon3sl.cms.controller.util.country;

import com.alcon3sl.cms.model.util.country.Country;
import com.alcon3sl.cms.services.util.country.DbCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "country")
public class CountryController {
    private final DbCountryService countryService;
    @Autowired
    public CountryController(DbCountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<Page<Country>> findAll(@RequestParam Optional<String> filter, Pageable pageable) {
        var page = countryService.findAll(
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

    @GetMapping(path = "{countryId}")
    public ResponseEntity<Country> findById(@PathVariable(name = "countryId") Long countryId) {
        return new ResponseEntity<>(countryService.findById(countryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> save(@RequestPart(name = "country") Country country) {
        return new ResponseEntity<>(countryService.save(country), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{countryId}")
    public ResponseEntity<Country> deleteById(@PathVariable(name = "countryId") Long countryId) {
        return new ResponseEntity<>(countryService.deleteById(countryId), HttpStatus.OK);
    }

    @PutMapping(path = "{countryId}")
    public ResponseEntity<Country> updateById(
            @PathVariable(name = "countryId") Long countryId,
            @RequestPart(name = "country") Country tempData
    ) {
        return new ResponseEntity<>(countryService.updateById(countryId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Country>> deleteAllById(
            @RequestParam Optional<List<Long>> listId
    ) {
        var countryList = countryService.deleteAllById(listId.orElse(new ArrayList<>()));
        if (countryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(path = { "findAll/{name}", "findAll/"} )
    public ResponseEntity<List<Country>> findAllNyName(
            @PathVariable(name = "name") Optional<String> name
    ) {
        var countryList = countryService.findAll(name.orElse(""));
        if (countryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(path = "count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(countryService.count(), HttpStatus.OK);
    }

    @GetMapping(path = "findByState_NotNull")
    public ResponseEntity<List<Country>> findByState_NotNull(
            @RequestParam(name = "name") Optional<String> name
    ) {
        var countryList = countryService.findByState_NotNull(name.orElse(""));
        if (countryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }
}
