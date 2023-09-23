package com.alcon3sl.cms.repository.contact_details;

import com.alcon3sl.cms.model.contact_details.contact_details.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    @Query(value = "SELECT * FROM contact_details c WHERE c.id IN (?1)", nativeQuery = true)
    List<ContactDetails> findAllByListId(List<Long> listId);
    @Modifying
    @Query(value = "DELETE FROM contact_details c WHERE c.id IN (?1)", nativeQuery = true)
    void deleteAllByListId(List<Long> listId);
}
