package com.alcon3sl.cms.controller.util.contact_detail;

import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.alcon3sl.cms.services.util.contact_detail.DbContactDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "contact_details")
public class ContactDetailsController {
    private final DbContactDetailService contactDetailsService;
    @Autowired
    public ContactDetailsController(DbContactDetailService contactDetailsService) {
        this.contactDetailsService = contactDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDetails>> findAll() {
        var contactDetailsList = contactDetailsService.findAll();
        if (contactDetailsList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(contactDetailsList, HttpStatus.OK);
    }

    @GetMapping(path = "{contactDetailsID}")
    public ResponseEntity<ContactDetails> findById(@PathVariable(name = "contactDetailsId") Long contactDetailsId) {
        return new ResponseEntity<>(contactDetailsService.findById(contactDetailsId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContactDetails> save(@RequestBody ContactDetails contactDetails) {
        return new ResponseEntity<>(contactDetailsService.save(contactDetails), HttpStatus.OK);
    }

    @DeleteMapping(path = "{contactDetailsId}")
    public ResponseEntity<ContactDetails> deleteById(@PathVariable(name = "contactDetailsId") Long contactDetailsId) {
        return new ResponseEntity<>(contactDetailsService.deleteById(contactDetailsId), HttpStatus.OK);
    }

    @PutMapping(path = "{contactDetailsId}")
    public ResponseEntity<ContactDetails> updateById(
            @PathVariable(name = "contactDetailsId") Long contactDetailsId,
            @RequestBody ContactDetails tempData
    ) {
        return new ResponseEntity<>(contactDetailsService.updateById(contactDetailsId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<ContactDetails>> findAllByListId(
            @RequestParam Optional<List<Long>> listId
            ) {
        var contactDetailsList = contactDetailsService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (contactDetailsList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(contactDetailsList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<ContactDetails>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId
    ) {
        var contactDetailsList = contactDetailsService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (contactDetailsList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(contactDetailsList, HttpStatus.OK);
    }
}
