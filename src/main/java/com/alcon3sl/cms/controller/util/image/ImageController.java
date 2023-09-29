package com.alcon3sl.cms.controller.util.image;

import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.util.image.DbImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

record SaveResult (boolean error, String filename, String link) {}

@RestController
@RequestMapping(path = "images/db")
public class ImageController {
    private final DbImageService imageService;

    @Autowired
    public ImageController(DbImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(path = "{filename}")
    public ResponseEntity<Resource> retrieve(@PathVariable(name = "filename") String filename) {
        var image = imageService.getImage(filename);
        var body = new ByteArrayResource(image.getData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getMimeType())
                .body(body);
    }

    //@PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(path = "upload")
    public ResponseEntity<Iterable<Image>> upload(@RequestPart("files") List<MultipartFile> files) {
        return new ResponseEntity<>(imageService.saveAll(files), HttpStatus.OK);
    }


    /*
    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/images/db/" + filename)
                .toUriString();
    }
     */

}
