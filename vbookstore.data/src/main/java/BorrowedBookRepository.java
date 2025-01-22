package com.hd.data;

import com.hd.domain.Book;
import com.hd.domain.BorrowedBook;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BorrowedBookRepository extends PagingAndSortingRepository<BorrowedBook, Long> {

}
