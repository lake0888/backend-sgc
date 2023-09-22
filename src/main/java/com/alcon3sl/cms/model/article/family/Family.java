package com.alcon3sl.cms.model.article.family;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "FAMILY")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String code;

    @JsonIgnore
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "specialty_id", referencedColumnName = "id")
    private Specialty specialty;

    @JsonIgnore
    @OneToMany(mappedBy = "family", cascade = CascadeType.REMOVE)
    private List<SubFamily> subfamilies;

    public Family() {
        this("", "", "", new byte[0], new Specialty(), new ArrayList<>());
    }
    public Family(String name, String description, String code, byte[] image, Specialty specialty, List<SubFamily> subfamilies) {
        this(0L, name, description, code, image, specialty, subfamilies);
    }

    public Family(Long id, String name, String description, String code, byte[] image,
                  Specialty specialty, List<SubFamily> subfamilies) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.image = image;
        this.specialty = specialty;
        this.subfamilies = subfamilies;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Specialty getSpecialty() { return specialty; }

    public void setSpecialty(Specialty specialty) { this.specialty = specialty; }

    public List<SubFamily> getSubfamilies() {
        return subfamilies;
    }

    public void setSubfamilies(List<SubFamily> subfamilies) {
        this.subfamilies = subfamilies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Family family = (Family) o;
        return Objects.equals(id, family.id) && Objects.equals(name, family.name) && Objects.equals(description, family.description) && Objects.equals(code, family.code) && Arrays.equals(image, family.image) && Objects.equals(specialty, family.specialty) && Objects.equals(subfamilies, family.subfamilies);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, code, specialty, subfamilies);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", image=" + Arrays.toString(image) +
                ", specialty=" + specialty +
                ", subfamilies=" + subfamilies +
                '}';
    }
}
