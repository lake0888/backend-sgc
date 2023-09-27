package com.alcon3sl.cms.model.util.contact_details;

import com.alcon3sl.cms.model.util.image.Image;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contact_details")
public class ContactDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String home_phone;
    private String work_phone;
    private String cell_phone;
    private String email;
    private String website;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public ContactDetails() {
        this("", "", "", "", "", new Image());
    }

    public ContactDetails(String home_phone, String work_phone, String cell_phone, String email, String website, Image image) {
        this(0L, home_phone, work_phone, cell_phone, email, website, image);
    }

    public ContactDetails(Long id, String home_phone, String work_phone, String cell_phone, String email, String website, Image image) {
        this.id = id;
        this.home_phone = home_phone;
        this.work_phone = work_phone;
        this.cell_phone = cell_phone;
        this.email = email;
        this.website = website;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHome_phone() {
        return home_phone;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public String getWork_phone() {
        return work_phone;
    }

    public void setWork_phone(String work_phone) {
        this.work_phone = work_phone;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetails that = (ContactDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(home_phone, that.home_phone) && Objects.equals(work_phone, that.work_phone) && Objects.equals(cell_phone, that.cell_phone) && Objects.equals(email, that.email) && Objects.equals(website, that.website) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, home_phone, work_phone, cell_phone, email, website, image);
    }

    @Override
    public String toString() {
        return "Contact_Details{" +
                "id=" + id +
                ", home_phone='" + home_phone + '\'' +
                ", work_phone='" + work_phone + '\'' +
                ", cell_phone='" + cell_phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
