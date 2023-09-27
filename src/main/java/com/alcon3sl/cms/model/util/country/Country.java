package com.alcon3sl.cms.model.util.country;

import com.alcon3sl.cms.model.util.countrystate.CountryState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String nationality;
    @Column(nullable = false)
    private String code;
    private String phoneCode;
    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE)
    private List<CountryState> stateList;

    public Country() {
        this("", "", "", "", new ArrayList<CountryState>());
    }
    public Country(String name, String nationality, String code,
                   String phoneCode, List<CountryState> stateList) {
        this(0L, name, nationality, code, phoneCode, stateList);
    }
    public Country(Long id, String name, String nationality, String code,
                   String phoneCode, List<CountryState> stateList) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.code = code;
        this.phoneCode = phoneCode;
        this.stateList = stateList;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public List<CountryState> getStateList() {
        return stateList;
    }
    public void setStateList(List<CountryState> stateList) {
        this.stateList = stateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) && Objects.equals(name, country.name) && Objects.equals(nationality, country.nationality) && Objects.equals(code, country.code) && Objects.equals(phoneCode, country.phoneCode) && Objects.equals(stateList, country.stateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nationality, code, phoneCode, stateList);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                ", code='" + code + '\'' +
                ", phoneCode='" + phoneCode + '\'' +
                ", stateList=" + stateList +
                '}';
    }
}
