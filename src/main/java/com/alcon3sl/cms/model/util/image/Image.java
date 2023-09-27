package com.alcon3sl.cms.model.util.image;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    @Column(name = "mime_type")
    private String mimeType;
    private byte[] data;

    public Image() {
        this("", "", new byte[0]);
    }
    public Image(String filename, String mimeType, byte[] data) {
        this(0L, filename, mimeType, data);
    }
    public Image(Long id, String filename, String mimeType, byte[] data) {
        this.id = id;
        this.filename = filename;
        this.mimeType = mimeType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(filename, image.filename) && Objects.equals(mimeType, image.mimeType) && Arrays.equals(data, image.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filename, mimeType);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
