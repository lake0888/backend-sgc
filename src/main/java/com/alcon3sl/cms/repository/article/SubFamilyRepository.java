package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubFamilyRepository extends JpaRepository<SubFamily, Long> {

    @Query(value = "SELECT s.id, s.code, s.name, s.description, s.image, s.family_id, " +
            "f.id as fId, f.code as fCode, f.name as fName, f.description as fDescription, " +
            "f.image as fImage, f.specialty_id, sp.id as spId, sp.code as spCode, " +
            "sp.name as spName, sp.description as spDescription, sp.image as spImage " +
            "FROM subfamily s " +
            "INNER JOIN family f ON (s.family_id = f.id) " +
            "INNER JOIN specialty sp ON (f.specialty_id = sp.id) " +
            "WHERE s.name ~* ?1 OR f.name ~* ?1 OR sp.name ~* ?1 " +
            "ORDER BY s.name ASC, f.name ASC, sp.name ASC", nativeQuery = true)
    List<SubFamily> findAllByNameOrFamilyOrSpecialty(String filter);
    @Query(value = "SELECT * FROM subfamily s WHERE UPPER(s.code) = ?1", nativeQuery = true)
    List<SubFamily> findByCode(String code);
    @Query(value = "SELECT * FROM subfamily s WHERE UPPER(s.name) = ?1", nativeQuery = true)
    List<SubFamily> findByName(String name);

    @Query(value = "SELECT * FROM subfamily s WHERE s.id IN (?1)", nativeQuery = true)
    List<SubFamily> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM subfamily s WHERE s.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
