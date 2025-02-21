package com.hd.vbookstore.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.util.ProxyUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String genre;
    private String language;
    private String ISBN;
    private String author;
    private Date publishDate;
    private int count;
    private float price;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column
    @UpdateTimestamp
    private Date updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || org.springframework.data.util.ProxyUtils.getUserClass(this) != org.springframework.data.util.ProxyUtils.getUserClass(o))
            return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public final int hashCode() {
        return ProxyUtils.getUserClass(this).hashCode();
    }
}