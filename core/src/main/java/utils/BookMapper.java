package com.hd.vbookstore.core.utils;


import com.hd.vbookstore.commons.BookDto;
import com.hd.vbookstore.domain.Book;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {

    BookDto bookDtoFromBook(Book book);

    Book bookFromBookDto(BookDto book);

}
