package com.hd.vbookstore.core.utils;


import com.hd.vbookstore.commons.BookDto;
import com.hd.vbookstore.domain.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {


    BookDto bookDtoFromBook(Book book);

    Book bookFromBookDto(BookDto book);

}
