package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.alcon3sl.cms.services.article.subfamily.DbSubFamilyService;
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
@RequestMapping(path = "subfamily")
public class SubFamilyController {
    private final DbSubFamilyService subFamilyService;

    @Autowired
    public SubFamilyController(DbSubFamilyService subFamilyService) {
        this.subFamilyService = subFamilyService;
    }

    @GetMapping
    public ResponseEntity<Page<SubFamily>> findAll(
            @RequestParam Optional<String> filter,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
    ) {
        var subfamilies = subFamilyService.findAll(filter.orElse(""),
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(50),
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
        if (subfamilies.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Page<SubFamily>>(subfamilies, HttpStatus.OK);
    }

    @GetMapping(path = "{subfamilyId}")
    public ResponseEntity<SubFamily> findById(
            @PathVariable(name = "subfamilyId") Long subfamilyId
    ) {
        return new ResponseEntity<>(subFamilyService.findById(subfamilyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubFamily> save(
            @RequestBody SubFamily subfamily
    ) {
        return new ResponseEntity<>(subFamilyService.save(subfamily), HttpStatus.OK);
    }

    @DeleteMapping(path = "{subfamilyId}")
    public ResponseEntity<SubFamily> deleteById(
            @PathVariable(name = "subfamilyId") Long subfamilyId
    ) {
        return new ResponseEntity<>(subFamilyService.deleteById(subfamilyId), HttpStatus.OK);
    }

    @PutMapping(path = "{subfamilyId}")
    public ResponseEntity<SubFamily> updateById(
            @PathVariable(name = "subfamilyId") Long subfamilyId,
            @RequestBody SubFamily tempData
    ) {
        return new ResponseEntity<>(subFamilyService.updateById(subfamilyId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<SubFamily>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var subfamilyList = subFamilyService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (subfamilyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(subfamilyList, HttpStatus.OK);
    }
    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<SubFamily>>deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var subfamilyList = subFamilyService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (subfamilyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(subfamilyList, HttpStatus.OK);
    }
}
