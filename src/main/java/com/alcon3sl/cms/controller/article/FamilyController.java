package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.article.family.DbFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Family> save(
            @RequestPart(name = "family") Family family,
            @RequestPart(name = "imageFile") MultipartFile imageFile) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        family.setImage(image);
        return new ResponseEntity<>(familyService.save(family), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{familyId}")
    public ResponseEntity<Family> deleteById(@PathVariable("familyId") Long familyId) {
        return new ResponseEntity<>(familyService.deleteById(familyId), HttpStatus.OK);
    }

    @PutMapping(path = "{familyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Family> updateById(
            @PathVariable("familyId") Long familyId,
            @RequestPart(name = "family") Family tempData,
            @RequestPart(name = "imageFile") MultipartFile imageFile
    ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        tempData.setImage(image);
        return new ResponseEntity<>(familyService.updateById(familyId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Family>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var familyList = familyService.deleteAllById(listId.orElse(new ArrayList<>()));
        if (familyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(familyList, HttpStatus.OK);
    }

    @GetMapping(path = "familyListBySpecialty_Id/{specialtyId}")
    public ResponseEntity<List<Family>> findBySpecialty_Id(
            @PathVariable("specialtyId") Long specialtyId
    ) {
        var familyList = familyService.findBySpecialty_Id(specialtyId);
        if (familyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<Family>>(familyList, HttpStatus.OK);
    }
}
