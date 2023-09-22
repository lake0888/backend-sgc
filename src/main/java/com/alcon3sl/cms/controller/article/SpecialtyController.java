package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.alcon3sl.cms.services.article.SpecialtyService;
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
@RequestMapping(path = "specialty")
public class SpecialtyController {
    private final SpecialtyService specialtyService;
    @Autowired
    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }
    @GetMapping
    public ResponseEntity<Page<Specialty>> findAll(
            @RequestParam Optional<String> name,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var specialties = specialtyService.findAll(name.orElse(""),
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(50),
                        Sort.Direction.ASC, sortBy.orElse("name")
                )
        );
        if (specialties.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Page<Specialty>>(specialties, HttpStatus.OK);
    }

    @GetMapping(path = "{specialtyId}")
    public ResponseEntity<Specialty> findById(
            @PathVariable("specialtyId") Long specialtyId) {
        return new ResponseEntity<>(specialtyService.findById(specialtyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Specialty> save(@RequestBody Specialty specialty) {
        return new ResponseEntity<>(specialtyService.save(specialty), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "{specialtyId}")
    public ResponseEntity<Specialty> deleteById(
            @PathVariable("specialtyId") Long specialtyId) {
        return new ResponseEntity<>(specialtyService.deleteById(specialtyId), HttpStatus.OK);
    }

    @PutMapping(path = "{specialtyId}")
    public ResponseEntity<Specialty> updateById(
            @PathVariable("specialtyId") Long specialtyId,
            @RequestBody Specialty tempData
    ) {
        return new ResponseEntity<>(
                specialtyService.updateById(specialtyId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<Specialty>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var specialtyList = specialtyService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (specialtyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(specialtyList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<Specialty>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var specialtyList = specialtyService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (specialtyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(specialtyList, HttpStatus.OK);
    }
}
