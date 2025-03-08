package com.hd.vbookstore.commons;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDto implements Serializable {


    private Long id;
    private String title;
    private String genre;
    private String language;
    private String ISBN;
    private String author;
    private Date publishDate;
    private int count;
    private float price;

}
