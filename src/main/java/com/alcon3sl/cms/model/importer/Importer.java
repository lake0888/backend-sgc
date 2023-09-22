package com.alcon3sl.cms.model.importer;

import com.alcon3sl.cms.model.contact_details.address.Address;
import com.alcon3sl.cms.model.contact_details.contact_details.ContactDetails;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "importer")
public class Importer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String nit;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "contact_details_id", referencedColumnName = "id")
    private ContactDetails contactDetails;

    public Importer() {
        this("", "", "", new Address(), new ContactDetails());
    }

    public Importer(String name, String description, String nit, Address address, ContactDetails contactDetails) {
        this(0L, name, description, nit, address, contactDetails);
    }

    public Importer(Long id, String name, String description, String nit, Address address, ContactDetails contactDetails) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nit = nit;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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
        Importer importer = (Importer) o;
        return Objects.equals(id, importer.id) && Objects.equals(name, importer.name) &&
                Objects.equals(description, importer.description) && Objects.equals(nit, importer.nit) &&
                Objects.equals(address, importer.address) && Objects.equals(contactDetails, importer.contactDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nit, address, contactDetails);
    }

    @Override
    public String toString() {
        return "Importer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + "\'" +
                ", nit='" + nit + '\'' +
                ", address=" + address + '\'' +
                ", contactDetails=" + contactDetails +
                '}';
    }
}
