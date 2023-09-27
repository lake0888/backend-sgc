package com.alcon3sl.cms.repository.util.country;

import com.alcon3sl.cms.model.util.country.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = "SELECT COUNT(*) FROM country c", nativeQuery = true)
    Long numberOfElements();
    @Query(value = "SELECT * FROM country c WHERE c.code ~* ?1 OR c.name ~* ?1", nativeQuery = true)
    Page<Country> findAllByCodeOrName(String filter, PageRequest pageRequest);
    @Query(value = "SELECT * FROM country c WHERE UPPER(c.code) = ?1", nativeQuery = true)
    List<Country> findByCode(String code);

    @Query(value = "SELECT * FROM country c WHERE UPPER(c.name) = ?1", nativeQuery = true)
    List<Country> findByName(String name);

    @Query(value = "SELECT * FROM country c WHERE UPPER(c.nationality) = ?1", nativeQuery = true)
    List<Country> findByNationality(String nationality);

    @Query(value = "SELECT * FROM country c WHERE UPPER(c.phone_code) = ?1", nativeQuery = true)
    List<Country> findByPhoneCode(String phoneCode);

    @Query(value = "SELECT * FROM country c WHERE c.id IN (?1)", nativeQuery = true)
    List<Country> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM country c WHERE c.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
