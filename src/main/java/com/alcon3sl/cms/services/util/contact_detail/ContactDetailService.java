package com.alcon3sl.cms.services.util.contact_detail;

import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactDetailService {
    List<ContactDetails> findAll();

    ContactDetails findById(Long contactDetailsId);

    ContactDetails save(ContactDetails contactDetails);

    ContactDetails deleteById(Long contactDetailsId);

    ContactDetails updateById(Long contactDetailsId, ContactDetails tempData);

    List<ContactDetails> deleteAllById(List<Long> listId);
}
