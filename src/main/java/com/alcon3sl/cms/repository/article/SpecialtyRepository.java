package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    @Query(value = "SELECT * FROM SPECIALTY s WHERE s.name ~* ?1 ORDER BY s.name ASC", nativeQuery = true)
    Page<Specialty> findAllByName(String name, PageRequest id);
    @Query(value = "SELECT * FROM SPECIALTY s WHERE UPPER(s.code) = ?1", nativeQuery = true)
    List<Specialty> findByCode(String code);

    @Query(value = "SELECT * FROM SPECIALTY s WHERE UPPER(s.name) = ?1", nativeQuery = true)
    List<Specialty> findByName(String name);

    @Query(value = "SELECT * FROM specialty s WHERE s.id IN (?1)", nativeQuery = true)
    List<Specialty> findAllByListId(List<Long> listId);
    @Modifying
    @Query(value = "DELETE FROM specialty s WHERE s.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
