package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    @Query(value = "SELECT * FROM manufacturer m WHERE m.name ~* ?1", nativeQuery = true)
    Page<Manufacturer> findAllByName(String name);
    List<Manufacturer> findAllByNameOrderByName(String name);

    Manufacturer findByNameIgnoreCase(String name);
}
