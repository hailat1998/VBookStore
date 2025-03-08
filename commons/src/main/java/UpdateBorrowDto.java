package com.hd.vbookstore.commons;

import com.hd.vbookstore.domain.enums.BorrowStatus;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateBorrowDto {
    Long borrow_id;
    BorrowStatus status;
}
