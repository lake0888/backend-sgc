package com.alcon3sl.cms.repository.carrier;

import com.alcon3sl.cms.model.carrier.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {
    @Query(value = "SELECT * FROM carrier c WHERE c.name ~* ?1 OR c.description ~* ?1 OR c.kind_carrier ~* ?1", nativeQuery = true)
    List<Carrier> findAllByNameOrKindCarrier(String filter);

    @Query(value = "SELECT * FROM carrier c WHERE UPPER(c.name) = ?1", nativeQuery = true)
    List<Carrier> findByName(String name);

    @Query(value = "SELECT * FROM carrier c WHERE UPPER(c.cif) = ?1", nativeQuery = true)
    List<Carrier> findByCif(String cif);

    @Query(value = "SELECT * FROM carrier c WHERE c.id IN (?1)", nativeQuery = true)
    List<Carrier> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM carrier c WHERE c.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);

}
