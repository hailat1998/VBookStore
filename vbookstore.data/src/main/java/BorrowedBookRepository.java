package com.hd.vbookstore.data;


import com.hd.vbookstore.domain.Book;
import com.hd.vbookstore.domain.BorrowedBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends PagingAndSortingRepository<BorrowedBook, Long> {

    BorrowedBook save(BorrowedBook borrowedBook);


    Optional<BorrowedBook> getBorrowById(Long bookId);

}
