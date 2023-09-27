package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.services.article.family.DbFamilyService;
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
@RequestMapping(path = "family")
public class FamilyController {
    private final DbFamilyService familyService;

    @Autowired
    public FamilyController(DbFamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping
    public ResponseEntity<Page<Family>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var families = familyService.findAll(filter.orElse(""),
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(50),
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
        if (families.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Page<Family>>(families, HttpStatus.OK);
    }

    @GetMapping(path = "{familyId}")
    public ResponseEntity<Family> findById(@PathVariable("familyId") Long familyId) {
        return new ResponseEntity<>(familyService.findById(familyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Family> save(@RequestBody Family family) {
        return new ResponseEntity<>(familyService.save(family), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{familyId}")
    public ResponseEntity<Family> deleteById(@PathVariable("familyId") Long familyId) {
        return new ResponseEntity<>(familyService.deleteById(familyId), HttpStatus.OK);
    }

    @PutMapping(path = "{familyId}")
    public ResponseEntity<Family> updateById(
            @PathVariable("familyId") Long familyId,
            @RequestBody Family tempData
    ) {
        return new ResponseEntity<>(familyService.updateById(familyId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "familyListBySpecialtyId/{specialtyId}")
    public ResponseEntity<List<Family>> findAllBySpecialtyId(
            @PathVariable("specialtyId") Long specialtyId
    ) {
        var familyList = familyService.findAllBySpecialtyId(specialtyId);
        if (familyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Family>>(familyList, HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<Family>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var familyList = familyService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (familyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(familyList, HttpStatus.OK);
    }
    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<Family>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var familyList = familyService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (familyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(familyList, HttpStatus.OK);
    }
}
