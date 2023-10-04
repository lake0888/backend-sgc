package com.alcon3sl.cms.repository.importer;

import com.alcon3sl.cms.model.importer.Importer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImporterRepository extends JpaRepository<Importer, Long> {

    @Query(value = "SELECT * FROM importer i WHERE i.name ~* ?1", nativeQuery = true)
    List<Importer> findAllByName(String name);

    @Query(value = "SELECT * FROM importer i WHERE UPPER(i.name) = ?1", nativeQuery = true)
    List<Importer> findByName(String name);
    @Query(value = "SELECT * FROM importer i WHERE UPPER(i.nit) = ?1", nativeQuery = true)
    List<Importer> findByNit(String nit);
}
