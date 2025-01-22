package com.hd.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class BorrowedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private final User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private final Book book;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private final Date start_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private final Date end_date;


    @Enumerated(EnumType.STRING)
    private BorrowStatus status;


    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;
}


 enum BorrowStatus {
    BORROWED,
    RETURNED,
    OVERDUE,
    LOST
}