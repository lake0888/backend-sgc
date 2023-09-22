package com.alcon3sl.cms.model.contact_details.country.countrystate;

import com.alcon3sl.cms.model.contact_details.address.Address;
import com.alcon3sl.cms.model.contact_details.country.country.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "country_state")
public class CountryState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "code_country", referencedColumnName = "code")
    private Country country;
    @JsonIgnore
    @OneToMany(mappedBy = "countryState", cascade = CascadeType.REMOVE)
    private List<Address> addressList;

    public CountryState() {
        this("", new Country(), new ArrayList<>());
    }
    public CountryState(String name, Country country, List<Address> addressList) {
        this(0L, name, country, addressList);
    }
    public CountryState(Long id, String name, Country country, List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.addressList = addressList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryState that = (CountryState) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(country, that.country) && Objects.equals(addressList, that.addressList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, addressList);
    }

    @Override
    public String toString() {
        return "CountryState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
