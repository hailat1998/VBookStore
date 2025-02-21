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
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class BorrowedBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private final User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private final Book book;

    @Column(nullable = false)
    private final Date start_date;

    @Column(nullable = false)
    private final Date end_date;

    @Enumerated(EnumType.STRING)
    private BorrowStatus status;


    @Column(updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column
    @UpdateTimestamp
    private Date updatedAt;


    public void setStatus(BorrowStatus status) {
        this.status = switch (status) {
            case BORROWED -> BorrowStatus.BORROWED;

            case RETURNED -> BorrowStatus.RETURNED ;

            case LOST -> BorrowStatus.LOST;

            case OVERDUE -> BorrowStatus.OVERDUE ;

        };
    }

}



