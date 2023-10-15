package com.alcon3sl.cms.controller.article;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.article.specialty.DbSpecialtyService;
import jakarta.websocket.server.PathParam;
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

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "specialty")
public class SpecialtyController {
    private final DbSpecialtyService specialtyService;
    @Autowired
    public SpecialtyController(DbSpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public ResponseEntity<Page<Specialty>> findAll(@RequestParam Optional<String> name, Pageable pageable) {
        Page<Specialty> page = specialtyService.findAll(
                name.orElse(""),
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

    @GetMapping(path = "{specialtyId}")
    public ResponseEntity<Specialty> findById(
            @PathVariable("specialtyId") Long specialtyId) {
        return new ResponseEntity<>(specialtyService.findById(specialtyId), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Specialty> save(
            @RequestPart("specialty") Specialty specialty,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        specialty.setImage(image);
        return new ResponseEntity<>(specialtyService.save(specialty), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "{specialtyId}")
    public ResponseEntity<Specialty> deleteById(
            @PathVariable("specialtyId") Long specialtyId) {
        return new ResponseEntity<>(specialtyService.deleteById(specialtyId), HttpStatus.OK);
    }

    @PutMapping(path = "{specialtyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Specialty> updateById(
            @PathVariable("specialtyId") Long specialtyId,
            @RequestPart(name = "specialty") Specialty tempData,
            @RequestPart(name = "imageFile") MultipartFile imageFile
    ) throws IOException {
        Image image = new Image(
                imageFile.getOriginalFilename(),
                imageFile.getContentType(),
                imageFile.getBytes()
        );
        tempData.setImage(image);
        return new ResponseEntity<>(
                specialtyService.updateById(specialtyId, tempData), HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllById")
    public ResponseEntity<List<Specialty>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var specialtyList = specialtyService.deleteAllById(listId.orElse(new ArrayList<>()));
        if (specialtyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(specialtyList, HttpStatus.OK);
    }

    @GetMapping(path = "findAll")
    public ResponseEntity<List<Specialty>> findAll(
            @RequestParam(name = "name") Optional<String> name
    ){
        var specialtyList = specialtyService.findAll(name.orElse(""));
        return new ResponseEntity<>(specialtyList, HttpStatus.OK);
    }

    @GetMapping(path = "findByFamily_NotNull")
    public ResponseEntity<List<Specialty>> findByFamily_NotNull(
            @RequestParam(name = "name") Optional<String> name
    ) {
        var specialtyList = specialtyService.findByFamily_NotNull(name.orElse(""));
        if (specialtyList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(specialtyList, HttpStatus.OK);
    }
}
