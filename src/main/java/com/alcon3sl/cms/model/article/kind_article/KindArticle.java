package com.alcon3sl.cms.model.article.kind_article;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "kind_article")
public class KindArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public KindArticle() {
        this("");
    }
    public KindArticle(String name) {
        this(0L, name);
    }
    public KindArticle(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindArticle that = (KindArticle) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "KindArticle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
