package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.article.subfamily.DbSubFamilyService;
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
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
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
    public ResponseEntity<Page<SubFamily>> findAll(@RequestParam Optional<String> filter, Pageable pageable) {
        var page = subFamilyService.findAll(
                filter.orElse(""),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                )
        );
        if (page.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<Page<SubFamily>>(page, HttpStatus.OK);
    }

    @GetMapping(path = "{subfamilyId}")
    public ResponseEntity<SubFamily> findById(@PathVariable(name = "subfamilyId") Long subfamilyId) {
        return new ResponseEntity<>(subFamilyService.findById(subfamilyId), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubFamily> save(
            @RequestPart(name = "subfamily") SubFamily subfamily,
            @RequestPart(name = "imageFile") MultipartFile imageFile
            ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        subfamily.setImage(image);
        return new ResponseEntity<>(subFamilyService.save(subfamily), HttpStatus.OK);
    }

    @DeleteMapping(path = "{subfamilyId}")
    public ResponseEntity<SubFamily> deleteById(
            @PathVariable(name = "subfamilyId") Long subfamilyId
    ) {
        return new ResponseEntity<>(subFamilyService.deleteById(subfamilyId), HttpStatus.OK);
    }

    @PutMapping(path = "{subfamilyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SubFamily> updateById(
            @PathVariable(name = "subfamilyId") Long subfamilyId,
            @RequestPart(name = "subfamily") SubFamily tempData,
            @RequestPart(name = "imageFile") MultipartFile imageFile
            ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        tempData.setImage(image);
        return new ResponseEntity<>(subFamilyService.updateById(subfamilyId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<SubFamily>>deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var subfamilyList = subFamilyService.deleteAllById(listId.orElse(new ArrayList<>()));
        if (subfamilyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(subfamilyList, HttpStatus.OK);
    }
}
