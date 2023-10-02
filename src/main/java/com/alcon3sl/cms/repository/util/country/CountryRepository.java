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
    @Query(value = "SELECT * FROM country c WHERE c.code ~* ?1 OR c.name ~* ?1", nativeQuery = true)
    Page<Country> findAllByCodeOrName(String filter, PageRequest pageRequest);

    List<Country> findByCodeIgnoreCase(String code);

    List<Country> findByNameIgnoreCase(String name);

    List<Country> findByNationalityIgnoreCase(String nationality);

    List<Country> findByPhoneCodeIgnoreCase(String phoneCode);

    @Query(value = "SELECT * FROM country c WHERE c.name ~* ?1 ORDER BY c.name ASC", nativeQuery = true)
    List<Country> findAllByName(String name);
}
