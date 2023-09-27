package com.alcon3sl.cms.model.article.subfamily;

import com.alcon3sl.cms.model.article.family.Family;
import com.alcon3sl.cms.model.util.image.Image;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "subfamily")
public class SubFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String code;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    @ManyToOne
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    private Family family;

    public SubFamily() {
        this("", "", "", new Image(), new Family());
    }
    public SubFamily(String name, String description, String code, Image image, Family family) {
        this(0L, name, description, code, image, family);
    }
    public SubFamily(Long id, String name, String description, String code, Image image, Family family) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.image = image;
        this.family = family;
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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubFamily subFamily = (SubFamily) o;
        return Objects.equals(id, subFamily.id) && Objects.equals(name, subFamily.name) && Objects.equals(description, subFamily.description) &&
                Objects.equals(code, subFamily.code) && Objects.equals(image, subFamily.image) && Objects.equals(family, subFamily.family);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, description, code, image, family);
        return result;
    }

    @Override
    public String toString() {
        return "SubFamily{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", image=" + image + '\'' +
                ", family=" + family +
                '}';
    }
}
