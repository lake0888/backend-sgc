package com.alcon3sl.cms.services.util.address;

import com.alcon3sl.cms.model.util.address.Address;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddressService {
    List<Address> findAll();

    Address findById(Long addressId);

    Address save(Address address);

    Address deleteById(Long addressId);

    Address updateById(Long addressId, Address tempData);

    List<Address> deleteAllById(List<Long> listId);
}
