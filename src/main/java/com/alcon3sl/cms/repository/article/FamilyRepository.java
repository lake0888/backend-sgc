package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.family.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query(value = "SELECT f.id, f.code, f.name, f.description, f.specialty_id, f.image_id, " +
            "s.id as sId, s.code as sCode, s.name as sName, s.description as sDescription, " +
            "s.image_id as sImage FROM family f " +
            "INNER JOIN specialty s ON (f.specialty_id = s.id) " +
            "WHERE f.name ~* ?1 OR s.name ~* ?1 ORDER BY f.name ASC, s.name ASC", nativeQuery = true)
    List<Family> findAllByNameOrSpecialty(String filter);

    List<Family> findByCodeIgnoreCase(String code);

    List<Family> findByNameIgnoreCase(String name);

    List<Family> findBySpecialty_Id(Long specialtyId);
}
