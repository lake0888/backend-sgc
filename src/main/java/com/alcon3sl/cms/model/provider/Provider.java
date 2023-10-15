package com.alcon3sl.cms.model.provider;

import com.alcon3sl.cms.model.util.address.Address;
import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String cif;
    private boolean isMaker;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_details_id", referencedColumnName = "id")
    private ContactDetails contactDetails;

    public Provider() {
        this("", "", "", false, new Address(), new ContactDetails());
    }

    public Provider(String name, String description, String cif, boolean isMaker, Address address, ContactDetails contactDetails) {
        this(0L, name, description, cif, isMaker, address, contactDetails);
    }

    public Provider(Long id, String name, String description, String cif, boolean isMaker, Address address, ContactDetails contactDetails) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cif = cif;
        this.isMaker = isMaker;
        this.address = address;
        this.contactDetails = contactDetails;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public boolean isMaker() {
        return isMaker;
    }

    public void setMaker(boolean maker) {
        isMaker = maker;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(id, provider.id) && Objects.equals(name, provider.name) &&
                Objects.equals(description, provider.description) && Objects.equals(cif, provider.cif) &&
                Objects.equals(isMaker, provider.isMaker) && Objects.equals(address, provider.address) &&
                Objects.equals(contactDetails, provider.contactDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cif, isMaker, address, contactDetails);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cif='" + cif + '\'' +
                ", isMaker='" + isMaker + '\'' +
                ", address='" + address + '\'' +
                ", contactDetails=" + contactDetails +
                '}';
    }
}
