package com.alcon3sl.cms.repository.contact_details;

import com.alcon3sl.cms.model.contact_details.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM address a WHERE a.id IN (?1)", nativeQuery = true)
    List<Address> findAllByListId(List<Long> listId);

    @Modifying
    @Query(value = "DELETE FROM address a WHERE a.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
