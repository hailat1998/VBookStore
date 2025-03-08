package com.hd.vbookstore.domain;

import com.hd.vbookstore.domain.enums.BorrowStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BorrowedBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Date end_date;

    @Setter
    @Enumerated(EnumType.STRING)
    private BorrowStatus status = BorrowStatus.BORROWED; // Default status

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column
    @UpdateTimestamp
    private Date updatedAt;


    public BorrowedBook(User user, Book book, Date start_date, Date end_date) {
        this.user = user;
        this.book = book;
        this.start_date = start_date;
        this.end_date = end_date;
    }

}



