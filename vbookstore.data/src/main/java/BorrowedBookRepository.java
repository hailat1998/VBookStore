package com.hd.vbookstore.data;


import com.hd.vbookstore.domain.BorrowedBook;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowedBookRepository extends PagingAndSortingRepository<BorrowedBook, Long> {

}
