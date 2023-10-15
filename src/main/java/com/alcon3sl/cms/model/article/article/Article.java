package com.alcon3sl.cms.model.article.article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
import com.alcon3sl.cms.model.article.subfamily.SubFamily;
import com.alcon3sl.cms.model.provider.Provider;
import com.alcon3sl.cms.model.util.coin.Coin;
import com.alcon3sl.cms.model.util.image.Image;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code_mf;//reference
    private String code_sa;
    private String name;
    private String description;
    private double weight;
    private double volume;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "subfamily_id", referencedColumnName = "id")
    private SubFamily subfamily;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "kind_article_id", referencedColumnName = "id")
    private KindArticle kindArticle;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    private Provider provider;
    private double initialCost;
    private double discount;
    private double margin;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coin_id", referencedColumnName = "id")
    private Coin coin;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public Article() {
        this("", "", "", "", 0, 0, new SubFamily(), new Manufacturer(), new KindArticle(),
                new Provider(), 0, 0, 1, new Coin(), new Image());
    }

    public Article(String code_mf, String code_sa, String name, String description,
                   double weight, double volume, SubFamily subfamily, Manufacturer manufacturer,
                   KindArticle kindArticle, Provider provider, double initialCost, double discount,
                   double margin, Coin coin, Image image) {
        this(0L, code_mf, code_sa, name, description, weight, volume, subfamily, manufacturer,
                kindArticle, provider, initialCost, discount, margin, coin, image);
    }

    public Article(Long id, String code_mf, String code_sa, String name, String description,
                   double weight, double volume, SubFamily subfamily, Manufacturer manufacturer,
                   KindArticle kindArticle, Provider provider,
                   double initialCost, double discount, double margin, Coin coin, Image image) {
        this.id = id;
        this.code_mf = code_mf;
        this.code_sa = code_sa;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.volume = volume;
        this.subfamily = subfamily;
        this.manufacturer = manufacturer;
        this.kindArticle = kindArticle;
        this.provider = provider;
        this.initialCost = initialCost;
        this.discount = discount;
        this.margin = margin;
        this.coin = coin;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode_mf() {
        return code_mf;
    }

    public void setCode_mf(String code_mf) {
        this.code_mf = code_mf;
    }

    public String getCode_sa() {
        return code_sa;
    }

    public void setCode_sa(String code_sa) {
        this.code_sa = code_sa;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public SubFamily getSubfamily() {
        return subfamily;
    }

    public void setSubfamily(SubFamily subfamily) {
        this.subfamily = subfamily;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public KindArticle getKindArticle() {
        return kindArticle;
    }

    public void setKindArticle(KindArticle kindArticle) {
        this.kindArticle = kindArticle;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public double getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(double initialCost) {
        this.initialCost = initialCost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
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
        Article article = (Article) o;
        return Double.compare(weight, article.weight) == 0 && Double.compare(volume, article.volume) == 0 &&
                Double.compare(initialCost, article.initialCost) == 0 && Double.compare(discount, article.discount) == 0 &&
                Double.compare(margin, article.margin) == 0 && Objects.equals(id, article.id) &&
                Objects.equals(code_mf, article.code_mf) && Objects.equals(code_sa, article.code_sa) &&
                Objects.equals(name, article.name) && Objects.equals(description, article.description) &&
                Objects.equals(subfamily, article.subfamily) && Objects.equals(manufacturer, article.manufacturer) &&
                Objects.equals(kindArticle, article.kindArticle) && Objects.equals(provider, article.provider) &&
                Objects.equals(coin, article.coin) && Objects.equals(image, article.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code_mf, code_sa, name, description, weight, volume,
                subfamily, manufacturer, kindArticle, provider, initialCost, discount,
                margin, coin, image);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", code_mf='" + code_mf + '\'' +
                ", code_sa='" + code_sa + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", volume=" + volume +
                ", subfamily=" + subfamily +
                ", manufacturer=" + manufacturer +
                ", kindArticle=" + kindArticle +
                ", provider=" + provider +
                ", initialCost=" + initialCost +
                ", discount=" + discount +
                ", margin=" + margin +
                ", coin=" + coin +
                ", image=" + image +
                '}';
    }

    public double grossPrice() { return this.initialCost * (1 - this.discount/100); }
    public double salePrice() { return grossPrice() * this.margin; }
}
