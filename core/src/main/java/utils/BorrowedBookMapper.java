package com.hd.vbookstore.core.utils;

import com.hd.vbookstore.commons.BorrowedBookResponseDto;
import com.hd.vbookstore.domain.BorrowedBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BorrowedBookMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullname", target = "userName")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "start_date", target = "startDate")
    @Mapping(source = "end_date", target = "endDate")
    @Mapping(source = "status", target = "status")
    BorrowedBookResponseDto toDto(BorrowedBook borrowedBook);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "userName", target = "user.fullname")
    @Mapping(source = "bookId", target = "book.id")
    @Mapping(source = "bookTitle", target = "book.title")
    @Mapping(source = "startDate", target = "start_date")
    @Mapping(source = "endDate", target = "end_date")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BorrowedBook toEntity(BorrowedBookResponseDto dto);


    List<BorrowedBookResponseDto> toDtoList(List<BorrowedBook> borrowedBooks);
    List<BorrowedBook> toEntityList(List<BorrowedBookResponseDto> dtos);
}