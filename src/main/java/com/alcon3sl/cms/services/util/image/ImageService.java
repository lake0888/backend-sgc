package com.alcon3sl.cms.services.util.image;

import com.alcon3sl.cms.model.util.image.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImage(String filename);

    Image save(MultipartFile file) throws Exception;

    Iterable<Image> saveAll(List<MultipartFile> files);
}
