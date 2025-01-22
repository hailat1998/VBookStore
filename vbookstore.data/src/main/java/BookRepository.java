package com.hd.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.hd.domain.Book;


public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

}
