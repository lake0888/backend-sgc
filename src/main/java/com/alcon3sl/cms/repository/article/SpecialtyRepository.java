package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    @Query(value = "SELECT * FROM specialty s WHERE s.name ~* ?1 ORDER BY s.name", nativeQuery = true)
    Optional<List<Specialty>> findAll(String name);

    List<Specialty> findByCodeIgnoreCase(String code);

    List<Specialty> findByNameIgnoreCase(String name);

    List<Specialty> findByFamilies_NotNull();
}
