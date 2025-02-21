package com.hd.vbookstore.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String title;
    private String genre;
    private String language;
    private String ISBN;
    private String author;
    private Date publishDate;
    private int count;
    private float price;


}
