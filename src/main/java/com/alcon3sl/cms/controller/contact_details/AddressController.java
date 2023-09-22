package com.alcon3sl.cms.controller.contact_details;

import com.alcon3sl.cms.model.contact_details.address.Address;
import com.alcon3sl.cms.services.contact_details.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "address")
public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        var addressList = addressService.findAll();
        if (addressList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @GetMapping(path = "{addressId}")
    public ResponseEntity<Address> findById(@PathVariable(name = "addressId") Long addressId) {
        return new ResponseEntity<>(addressService.findById(addressId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {
        return new ResponseEntity<>(addressService.save(address), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{addressId}")
    public ResponseEntity<Address> deleteById(@PathVariable(name = "addressId") Long addressId) {
        return new ResponseEntity<>(addressService.deleteById(addressId), HttpStatus.OK);
    }

    @PutMapping(path = "{addressId}")
    public ResponseEntity<Address> updateById(
            @PathVariable(name = "addressId") Long addressId,
            @RequestBody Address tempData) {
        return new ResponseEntity<>(addressService.updateById(addressId, tempData), HttpStatus.OK);
    }

    @GetMapping(path = "findAllByListId")
    public ResponseEntity<List<Address>> findAllByListId(
            @RequestParam Optional<List<Long>> listId) {
        var addressList = addressService.findAllByListId(listId.orElse(new ArrayList<>()));
        if (addressList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @DeleteMapping(path = "deleteAllByListId")
    public ResponseEntity<List<Address>> deleteAllByListId(
            @RequestParam Optional<List<Long>> listId) {
        var addressList = addressService.deleteAllByListId(listId.orElse(new ArrayList<>()));
        if (addressList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }
}
