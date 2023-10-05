package com.alcon3sl.cms.model.bank;

import com.alcon3sl.cms.model.util.address.Address;
import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private String swift;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_details_id", referencedColumnName = "id")
    private ContactDetails contactDetails;
    @JsonIgnore
    @OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
    private List<BankAccount> accounts;

    public Bank() {
        this("", "", "", new Address(), new ContactDetails(), new ArrayList<>());
    }

    public Bank(String code, String name, String swift, Address address, ContactDetails contactDetails, List<BankAccount> accounts) {
        this(0L, code, name, swift, address, contactDetails, accounts);
    }

    public Bank(Long id, String code, String name, String swift, Address address, ContactDetails contactDetails, List<BankAccount> accounts) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.swift = swift;
        this.address = address;
        this.contactDetails = contactDetails;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(code, bank.code) && Objects.equals(name, bank.name) && Objects.equals(swift, bank.swift) && Objects.equals(address, bank.address) && Objects.equals(contactDetails, bank.contactDetails) && Objects.equals(accounts, bank.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, swift, address, contactDetails, accounts);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", swift='" + swift + '\'' +
                ", address=" + address +
                ", contactDetails=" + contactDetails +
                ", accounts=" + accounts +
                '}';
    }
}
