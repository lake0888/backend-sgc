package com.alcon3sl.cms.repository.image;

import com.alcon3sl.cms.model.util.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFilename(String filename);

    boolean existsByFilename(String filename);
}
