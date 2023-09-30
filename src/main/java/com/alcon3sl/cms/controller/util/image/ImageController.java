package com.alcon3sl.cms.controller.util.image;

import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.services.util.image.DbImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

record SaveResult (boolean error, String filename, String link) {}

@RestController
@RequestMapping(path = "images")
public class ImageController {
    private static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";
    private final DbImageService imageService;

    public ImageController(DbImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(path = "upload")
    public ResponseEntity<List<Image>> uploadFiles(
            @RequestPart(name = "files") List<MultipartFile> files
    ) throws IOException {
        List<Image> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            Image image = this.newImage(file);
            Path fileStorage = Paths.get(DIRECTORY, image.getFilename()).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(image);
        }
        return new ResponseEntity<>(filenames, HttpStatus.OK);
    }

    private Image newImage(MultipartFile file) throws IOException {
        return new Image(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
    }

    @GetMapping(path = "{filename}")
    public ResponseEntity<Resource> retrieve(
            @PathVariable(name = "filename") String filename,
            @RequestParam(name = "flag") boolean flag) throws IOException {
        var image = (flag)
                ? imageService.getImage(filename)
                : this.getImageFromServer(filename);
        var body = new ByteArrayResource(image.getData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getMimeType())
                .body(body);
    }

    private Image getImageFromServer(String filename) throws IOException {
        Path filePath = Paths.get(DIRECTORY).toAbsolutePath().normalize().resolve(filename);
        if (!Files.exists(filePath))
            throw new FileNotFoundException(filename + " wasn't found on the server");

        return new Image(
                filename,
                MediaType.parseMediaType(Files.probeContentType(filePath)).toString(),
                new UrlResource(filePath.toUri()).getContentAsByteArray()
        );
    }


}
