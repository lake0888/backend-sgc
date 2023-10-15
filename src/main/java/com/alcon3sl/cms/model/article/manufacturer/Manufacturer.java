package com.alcon3sl.cms.model.article.manufacturer;

import com.alcon3sl.cms.model.util.image.Image;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Manufacturer() {
        this("", new Image());
    }
    public Manufacturer(String name, Image image) {
        this(0L, name, image);
    }
    public Manufacturer(Long id, String name, Image image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public Image getImage() { return this.image; }
    public void setImage(Image image) { this.image = image; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image);
    }

    @Override
    public String toString() {
        return "Manufacturer {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image +
                "}";
    }
}
