package com.alcon3sl.cms.controller.contact_details.country;

import com.alcon3sl.cms.model.contact_details.country.countrystate.CountryState;
import com.alcon3sl.cms.services.contact_details.country.CountryStateService;
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
@RequestMapping(path = "countrystate")
public class CountryStateController {
    private final CountryStateService countryStateService;
    @Autowired
    public CountryStateController(CountryStateService countryStateService) {
        this.countryStateService = countryStateService;
    }
    @GetMapping
    public ResponseEntity<Page<CountryState>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var countryStateList = countryStateService.findAll(filter.orElse(""),
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(50),
                        Sort.Direction.ASC, sortBy.orElse("name")));
        if (countryStateList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryStateList, HttpStatus.OK);
    }

    @GetMapping(path = "{countryStateId}")
    public ResponseEntity<CountryState> findById(
            @PathVariable(name = "countryStateId") Long countryStateId) {
        return new ResponseEntity<>(countryStateService.findById(countryStateId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CountryState> save(@RequestBody CountryState countryState) {
        return new ResponseEntity<>(countryStateService.save(countryState), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{countryStateId}")
    public ResponseEntity<CountryState> deleteById(
            @PathVariable(name = "countryStateId") Long countryStateId) {
        return new ResponseEntity<>(countryStateService.deleteById(countryStateId), HttpStatus.OK);
    }

    @PutMapping(path = "{countryStateId}")
    public ResponseEntity<CountryState> updateById(
            @PathVariable(name = "countryStateId") Long countryStateId,
            @RequestBody CountryState countryState
    ) {
        return new ResponseEntity<>(countryStateService.updateById(countryStateId, countryState), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<CountryState>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var countryStateList = countryStateService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (countryStateList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryStateList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<CountryState>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var countryStateList = countryStateService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (countryStateList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(countryStateList, HttpStatus.OK);
    }
}
