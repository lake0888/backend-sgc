package com.alcon3sl.cms.controller.importer;

import com.alcon3sl.cms.model.importer.Importer;
import com.alcon3sl.cms.services.importer.ImporterService;
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
@RequestMapping(path = "importer")
public class ImporterController {
    private final ImporterService importerService;
    @Autowired
    public ImporterController(ImporterService importerService) {
        this.importerService = importerService;
    }

    @GetMapping
    public ResponseEntity<Page<Importer>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
            ) {
        var importerList = importerService.findAll(filter.orElse(""), PageRequest.of(
                page.orElse(0),
                size.orElse(50),
                Sort.Direction.ASC, sortBy.orElse("name")));
        if (importerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(importerList, HttpStatus.OK);
    }

    @GetMapping(path = "{importerId}")
    public ResponseEntity<Importer> findById(@PathVariable(name = "importerId") Long importerId) {
        return new ResponseEntity<>(importerService.findById(importerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Importer> save(@RequestBody Importer importer) {
        return new ResponseEntity<>(importerService.save(importer), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{importerId}")
    public ResponseEntity<Importer> deleteById(@PathVariable(name = "importerId") Long importerId) {
        return new ResponseEntity<>(importerService.deleteById(importerId), HttpStatus.OK);
    }

    @PutMapping(path = "{importerId}")
    public ResponseEntity<Importer> updateById(
            @PathVariable(name = "importerId") Long importerId,
            @RequestBody Importer tempData
    ) {
        return new ResponseEntity<>(importerService.updateById(importerId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<Importer>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var importerList = importerService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (importerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(importerList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<Importer>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var importerList = importerService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (importerList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(importerList, HttpStatus.OK);
    }
}