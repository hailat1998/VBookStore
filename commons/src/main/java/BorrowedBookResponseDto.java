package com.hd.vbookstore.commons;


import com.hd.vbookstore.domain.enums.BorrowStatus;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BorrowedBookResponseDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long bookId;
    private String bookTitle;
    private Date startDate;
    private Date endDate;
    private BorrowStatus status;
}
