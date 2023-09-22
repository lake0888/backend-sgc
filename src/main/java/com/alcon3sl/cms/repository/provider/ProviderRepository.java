package com.alcon3sl.cms.repository.provider;

import com.alcon3sl.cms.model.provider.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query(value = "SELECT * FROM provider p WHERE p.name ~* ?1", nativeQuery = true)
    Page<Provider> findAllByName(String filter, PageRequest pageRequest);

    @Query(value = "SELECT * FROM provider p WHERE UPPER(p.name) = ?1", nativeQuery = true)
    List<Provider> findByName(String name);

    @Query(value = "SELECT * FROM provider p WHERE UPPER(p.cif) = ?1", nativeQuery = true)
    List<Provider> findByCif(String cif);

    @Query(value = "SELECT * FROM provider p WHERE p.id IN (?1)", nativeQuery = true)
    List<Provider> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM provider p WHERE p.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
