package com.hd.vbookstore.commons;

import com.hd.vbookstore.domain.enums.BorrowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBorrowDto {
    Long borrow_id;
    BorrowStatus status;
}
