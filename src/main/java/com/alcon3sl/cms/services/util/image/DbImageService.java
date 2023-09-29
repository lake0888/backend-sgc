package com.alcon3sl.cms.services.util.image;

import com.alcon3sl.cms.exception.ImageNotFoundException;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DbImageService implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public DbImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image getImage(String filename) {
        return imageRepository.findByFilename(filename)
                .orElseThrow(() -> new ImageNotFoundException("Image not found"));
    }

    @Override
    public Image save(MultipartFile file) throws Exception {
        if (imageRepository.existsByFilename(file.getOriginalFilename()))
            return this.getImage(file.getOriginalFilename());
        return imageRepository.save(this.newImage(file));
    }

    @Override
    public Iterable<Image> saveAll(List<MultipartFile> files) {
        var images = files.stream()
                .filter(file -> !imageRepository.existsByFilename(file.getOriginalFilename()))
                .map(this::newImage)
                .collect(Collectors.toList());
        return imageRepository.saveAll(images);
    }

    private Image newImage(MultipartFile file) {
        try {
            return new Image(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
