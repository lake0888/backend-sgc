package com.alcon3sl.cms.repository.article;

import com.alcon3sl.cms.model.article.family.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query(value = "SELECT f.id, f.code, f.name, f.description, f.image, f.specialty_id, " +
            "s.id as sId, s.code as sCode, s.name as sName, s.description as sDescription, " +
            "s.image as sImage FROM family f " +
            "INNER JOIN specialty s ON (f.specialty_id = s.id) " +
            "WHERE f.name ~* ?1 OR s.name ~* ?1 ORDER BY f.name ASC, s.name ASC", nativeQuery = true)
    List<Family> findAllByNameOrSpecialty(String filter);
    @Query(value = "SELECT * FROM family f WHERE UPPER(f.code) = ?1", nativeQuery = true)
    List<Family> findByCode(String code);

    @Query(value = "SELECT * FROM family f WHERE UPPER(f.name) = ?1", nativeQuery = true)
    List<Family> findByName(String name);

    @Query(value = "SELECT * FROM family f WHERE f.specialty_id = ?1 ORDER BY f.name ASC", nativeQuery = true)
    List<Family> findAllBySpecialtyId(Long specialtyId);

    @Query(value = "SELECT * FROM family f WHERE f.id IN (?1)", nativeQuery = true)
    List<Family> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM family f WHERE f.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
