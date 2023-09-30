package com.alcon3sl.cms.services.util.address;

import com.alcon3sl.cms.model.util.address.Address;
import com.alcon3sl.cms.exception.AddressNotFoundException;
import com.alcon3sl.cms.model.util.countrystate.CountryState;
import com.alcon3sl.cms.repository.util.address.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class DbAddressService implements AddressService {
    private final AddressRepository addressRepository;
    @Autowired
    public DbAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(()-> new AddressNotFoundException("Address not found"));
    }

    @Override
    public Address save(Address address) {
        boolean flag = address == null || address.getCountryState() == null || address.getCountryState().getName().isEmpty();
        if (flag)
            throw new AddressNotFoundException("Wrong data");
        return addressRepository.save(address);
    }

    @Override
    public Address deleteById(Long addressId) {
        var address = this.findById(addressId);
        addressRepository.deleteById(addressId);
        return address;
    }

    @Override
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

    @Override
    public List<Address> deleteAllById(List<Long> listId) {
        var addressList = addressRepository.findAllById(listId);
        addressRepository.deleteAllById(listId);
        return addressList;
    }
}
