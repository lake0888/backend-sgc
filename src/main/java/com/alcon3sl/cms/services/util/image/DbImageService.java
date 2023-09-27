package com.alcon3sl.cms.services.util.image;

import com.alcon3sl.cms.exception.ImageNotFoundException;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        Image image = new Image(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return imageRepository.save(image);
    }
}
