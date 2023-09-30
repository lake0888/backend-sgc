package com.alcon3sl.cms.model.article.specialty;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.model.util.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "SPECIALTY")
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String code;
    @OneToOne(cascade = { CascadeType.ALL, CascadeType.REMOVE }, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    @JsonIgnore
    @OneToMany(mappedBy = "specialty", cascade = CascadeType.REMOVE)
    private List<Family> families;
    public Specialty() {
        this("", "", "", new Image(), new ArrayList<>());
    }
    public Specialty(String name, String description, String code,
                     Image image, List<Family> families) {
        this(0L, name, description, code, image, families);
    }
    public Specialty(Long id, String name, String description, String code,
                     Image image, List<Family> families) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.image = image;
        this.families = families;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Family> getFamilies() { return families; }

    public void setFamilies(List<Family> families) {
        this.families = families;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(id, specialty.id) && Objects.equals(name, specialty.name) && Objects.equals(description, specialty.description) &&
                Objects.equals(code, specialty.code) && Objects.equals(image, specialty.image) && Objects.equals(families, specialty.families);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, code, image, families);
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", image=" + image +
                ", families=" + families +
                '}';
    }
}
