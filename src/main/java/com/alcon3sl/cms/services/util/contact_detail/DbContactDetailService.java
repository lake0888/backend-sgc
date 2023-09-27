package com.alcon3sl.cms.services.util.contact_detail;

import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.alcon3sl.cms.exception.ContactDetailNotFoundException;
import com.alcon3sl.cms.model.util.image.Image;
import com.alcon3sl.cms.repository.util.contact_detail.ContactDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DbContactDetailService implements ContactDetailService {
    private final ContactDetailRepository contactDetailsRepository;
    @Autowired
    public DbContactDetailService(ContactDetailRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public List<ContactDetails> findAll() {
        return contactDetailsRepository.findAll();
    }

    @Override
    public ContactDetails findById(Long contactDetailsId) {
        return contactDetailsRepository.findById(contactDetailsId)
                .orElseThrow(() -> new ContactDetailNotFoundException("Contact Details not found"));
    }

    @Override
    public ContactDetails save(ContactDetails contactDetails) {
        boolean flag = contactDetails == null;
        if (flag)
            throw new ContactDetailNotFoundException("Contact Details not found");
        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public ContactDetails deleteById(Long contactDetailsId) {
        var contactDetail = this.findById(contactDetailsId);
        contactDetailsRepository.deleteById(contactDetailsId);
        return contactDetail;
    }

    @Override
    public ContactDetails updateById(Long contactDetailsId, ContactDetails tempData) {
        var contactDetails = this.findById(contactDetailsId);

        String home_phone = tempData.getHome_phone();
        if (home_phone != null && !home_phone.isEmpty() && !Objects.equals(contactDetails.getHome_phone(), home_phone))
            contactDetails.setHome_phone(home_phone);

        String work_phone = tempData.getWork_phone();
        if (work_phone != null && !work_phone.isEmpty() && !Objects.equals(contactDetails.getWork_phone(), work_phone))
            contactDetails.setWork_phone(work_phone);

        String cell_phone = tempData.getCell_phone();
        if (cell_phone != null && !cell_phone.isEmpty() && !Objects.equals(contactDetails.getCell_phone(), cell_phone))
            contactDetails.setCell_phone(cell_phone);

        String email = tempData.getEmail();
        if (email != null && !email.isEmpty() && !Objects.equals(contactDetails.getEmail(), email))
            contactDetails.setEmail(email);

        String website = tempData.getWebsite();
        if (website != null && !website.isEmpty() && !Objects.equals(contactDetails.getWebsite(), website))
            contactDetails.setWebsite(website);

        Image image = tempData.getImage();
        if (image != null && !Objects.equals(contactDetails.getImage(), image))
            contactDetails.setImage(image);

        return contactDetailsRepository.save(contactDetails);
    }

    @Override
    public List<ContactDetails> findAllByListId(List<Long> listId) {
        return contactDetailsRepository.findAllByListId(listId);
    }

    @Override
    @Transactional
    public List<ContactDetails> deleteAllByListId(List<Long> listId) {
        var contactDetailsList = this.findAllByListId(listId);
        contactDetailsRepository.deleteAllByListId(listId);
        return contactDetailsList;
    }
}
