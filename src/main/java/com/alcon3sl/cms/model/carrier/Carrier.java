package com.alcon3sl.cms.model.carrier;

import com.alcon3sl.cms.model.contact_details.contact_details.ContactDetails;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "carrier")
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private String cif;
    @Convert(converter = KindCarrierConverter.class)
    private KindCarrier kindCarrier;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "contact_details_id", referencedColumnName = "id")
    private ContactDetails contactDetails;

    public Carrier() {
        this("", "", "", KindCarrier.Multimodal, new ContactDetails());
    }

    public Carrier(String name, String description, String cif, KindCarrier kindCarrier, ContactDetails contactDetails) {
        this(0L, name, description, cif, kindCarrier, contactDetails);
    }
    public Carrier(Long id, String name, String description, String cif, KindCarrier kindCarrier, ContactDetails contactDetails) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cif = cif;
        this.kindCarrier = kindCarrier;
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

    public KindCarrier getKindCarrier() {
        return kindCarrier;
    }

    public void setKindCarrier(KindCarrier kindCarrier) {
        this.kindCarrier = kindCarrier;
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
        Carrier carrier = (Carrier) o;
        return Objects.equals(id, carrier.id) && Objects.equals(name, carrier.name) &&
                Objects.equals(description, carrier.description) &&
                Objects.equals(cif, carrier.cif) && kindCarrier == carrier.kindCarrier &&
                Objects.equals(contactDetails, carrier.contactDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cif, kindCarrier, contactDetails);
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cif='" + cif + '\'' +
                ", kindCarrier=" + kindCarrier +
                ", contactDetails=" + contactDetails +
                '}';
    }
}
