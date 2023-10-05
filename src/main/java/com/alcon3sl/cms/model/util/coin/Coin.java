package com.alcon3sl.cms.model.util.coin;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "coin")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;

    public Coin() {
        this("", "");
    }
    public Coin(String code, String name) {
        this(0L, code, name);
    }
    public Coin(Long id, String code, String name) {
        Id = id;
        this.code = code;
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return Objects.equals(Id, coin.Id) && Objects.equals(code, coin.code) && Objects.equals(name, coin.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, code, name);
    }

    @Override
    public String toString() {
        return "Coin{" +
                "Id=" + Id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
