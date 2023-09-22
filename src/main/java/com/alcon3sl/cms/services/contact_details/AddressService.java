package com.alcon3sl.cms.services.contact_details;

import com.alcon3sl.cms.model.contact_details.address.Address;
import com.alcon3sl.cms.model.contact_details.address.AddressException;
import com.alcon3sl.cms.model.contact_details.country.countrystate.CountryState;
import com.alcon3sl.cms.repository.contact_details.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(()-> new AddressException("Address not found"));
    }

    public Address save(Address address) {
        boolean flag = address == null || address.getCountryState() == null || address.getCountryState().getName().isEmpty();
        if (flag)
            throw new AddressException("Wrong data");
        return addressRepository.save(address);
    }

    public Address deleteById(Long addressId) {
        var address = this.findById(addressId);
        addressRepository.deleteById(addressId);
        return address;
    }

    public Address updateById(Long addressId, Address tempData) {
        var address = this.findById(addressId);

        String addressLine = tempData.getAddressLine();
        if (addressLine != null && !addressLine.isEmpty() && !Objects.equals(address.getAddressLine(), addressLine))
            address.setAddressLine(addressLine);

        String city = tempData.getCity();
        if (city != null && !city.isEmpty() && !Objects.equals(address.getCity(), city))
            address.setCity(city);

        String county = tempData.getCounty();
        if (county != null && !county.isEmpty() && !Objects.equals(address.getCounty(), county))
            address.setCounty(county);

        int zipcode = tempData.getZipcode();
        if (!Objects.equals(address.getZipcode(), zipcode))
            address.setZipcode(zipcode);

        CountryState countryState = tempData.getCountryState();
        if (countryState != null && !Objects.equals(address.getCountryState(), countryState))
            address.setCountryState(countryState);

        return addressRepository.save(address);
    }

    public List<Address> findAllByListId(List<Long> listId) {
        return addressRepository.findAllByListId(listId);
    }

    @Transactional
    public List<Address> deleteAllByListId(List<Long> listId) {
        var addressList = this.findAllByListId(listId);
        addressRepository.deleteAllByListId(listId);
        return addressList;
    }
}
