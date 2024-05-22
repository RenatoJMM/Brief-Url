package com.briefurl.urlshortener.model;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String url;

    @Column(length = 6,nullable = false,unique = true)
    private String shortKey;

    @Column(nullable = false,unique = true)
    private String shortUrl;



    public Url() {
    }

    public Url(String url) {
        this.url = url;
    }

    public Url(String url, String shortKey, String shortUrl) {
        this.url = url;
        this.shortKey = shortKey;
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equals(this.url, url.url) && Objects.equals(shortKey, url.shortKey) && Objects.equals(shortUrl, url.shortUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, shortKey, shortUrl);
    }
}
