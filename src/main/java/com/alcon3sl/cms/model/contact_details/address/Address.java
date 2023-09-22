package com.alcon3sl.cms.model.contact_details.address;

import com.alcon3sl.cms.model.contact_details.country.countrystate.CountryState;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String addressLine;
    private String city;
    private String county;
    private int zipcode;
    @ManyToOne
    @JoinColumn(name = "country_state_id", referencedColumnName = "id")
    private CountryState countryState;

    public Address() {
        this("", "", "", 0, new CountryState());
    }

    public Address(String addressLine, String city, String county, int zipcode, CountryState countryState) {
        this(0L, addressLine, city, county, zipcode, countryState);
    }

    public Address(Long id, String addressLine, String city, String county, int zipcode, CountryState countryState) {
        this.id = id;
        this.addressLine = addressLine;
        this.city = city;
        this.county = county;
        this.zipcode = zipcode;
        this.countryState = countryState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public CountryState getCountryState() {
        return countryState;
    }

    public void setCountryState(CountryState countryState) {
        this.countryState = countryState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return zipcode == address.zipcode && Objects.equals(id, address.id) && Objects.equals(addressLine, address.addressLine) && Objects.equals(city, address.city) && Objects.equals(county, address.county) && Objects.equals(countryState, address.countryState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressLine, city, county, zipcode, countryState);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", zipcode=" + zipcode +
                ", countryState=" + countryState +
                '}';
    }
}
