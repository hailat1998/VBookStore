package com.hd.vbookstore.commons;


import com.hd.vbookstore.domain.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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
