package com.hd.vbookstore.commons;


import com.hd.vbookstore.domain.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
