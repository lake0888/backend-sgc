package com.alcon3sl.cms.controller.contact_details.country;

import com.alcon3sl.cms.model.contact_details.country.country.Country;
import com.alcon3sl.cms.services.contact_details.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final CountryService countryService;
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<Page<Country>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var countryList = countryService.findAll(filter.orElse(""), PageRequest.of(
                page.orElse(0),
                size.orElse(50),
                Sort.Direction.ASC, sortBy.orElse("name")));
        if (countryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @GetMapping(path = "{countryId}")
    public ResponseEntity<Country> findById(@PathVariable(name = "countryId") Long countryId) {
        return new ResponseEntity<>(countryService.findById(countryId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> save(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.save(country), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{countryId}")
    public ResponseEntity<Country> deleteById(@PathVariable(name = "countryId") Long countryId) {
        return new ResponseEntity<>(countryService.deleteById(countryId), HttpStatus.OK);
    }

    @PutMapping(path = "{countryId}")
    public ResponseEntity<Country> updateById(
            @PathVariable(name = "countryId") Long countryId,
            @RequestBody Country tempData
    ) {
        return new ResponseEntity<>(countryService.updateById(countryId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<Country>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var countryList = countryService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (countryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<Country>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var countryList = countryService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (countryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }
}
