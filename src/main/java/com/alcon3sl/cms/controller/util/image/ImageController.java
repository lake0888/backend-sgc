package com.alcon3sl.cms.controller.util.image;

import com.alcon3sl.cms.services.util.image.DbImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

    @PostMapping(path = "upload")
    public SaveResult upload(@RequestPart MultipartFile file) {
        try {
            var image = imageService.save(file);
            return new SaveResult(false, file.getOriginalFilename(),
                    createImageLink(file.getOriginalFilename()));
        } catch (Exception e) {
            return new SaveResult(true, file.getOriginalFilename(), "");
        }
    }

    @PostMapping(path = "upload_multi")
    public List<SaveResult> uploadMulti(@RequestPart List<MultipartFile> files) {
        return files.stream()
                .map(this::upload)
                .collect(Collectors.toList());
    }

    private String createImageLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/images/db/" + filename)
                .toUriString();
    }

}
