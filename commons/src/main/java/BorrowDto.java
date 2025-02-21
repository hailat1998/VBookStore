package com.hd.vbookstore.commons;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDto {
    Long book_id;
    Date start_date;
    Date end_date;
}
