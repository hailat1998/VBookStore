package com.hd.vbookstore.data.mapper;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorBookCountDTO {
    private String author;
    private Long bookCount;

    public AuthorBookCountDTO(String author, Long bookCount) {
        this.author = author;
        this.bookCount = bookCount;
    }
}
