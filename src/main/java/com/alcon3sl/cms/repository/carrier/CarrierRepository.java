package com.alcon3sl.cms.repository.carrier;

import com.alcon3sl.cms.model.carrier.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {
    @Query(value = "SELECT * FROM carrier c WHERE c.name ~* ?1 OR c.kind_carrier ~* ?1", nativeQuery = true)
    List<Carrier> findAllByNameOrKindCarrier(String filter);

    List<Carrier> findByNameIgnoreCase(String name);

    List<Carrier> findByCifIgnoreCase(String cif);

    List<Carrier> findByNameOrderByName(String name);
}
