package com.hd.vbookstore.core.services;

import com.hd.vbookstore.commons.BorrowedBookResponseDto;
import com.hd.vbookstore.commons.exceptions.NoDataFoundException;
import com.hd.vbookstore.core.utils.BorrowedBookMapper;
import com.hd.vbookstore.data.BookRepository;
import com.hd.vbookstore.data.BorrowedBookRepository;
import com.hd.vbookstore.data.UserRepository;
import com.hd.vbookstore.domain.Book;
import com.hd.vbookstore.domain.BorrowedBook;
import com.hd.vbookstore.domain.enums.BorrowStatus;
import com.hd.vbookstore.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class BorrowedBookService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowedBookMapper borrowedBookMapper;

    public BorrowedBookService(
            BorrowedBookRepository borrowedBookRepository,
            BookRepository bookRepository,
            UserRepository userRepository,
            BorrowedBookMapper borrowedBookMapper
           ) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowedBookMapper = borrowedBookMapper;
    }

    public BorrowedBookResponseDto setBorrow(Long bookId, Date startDate, Date endDate) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Book> book = bookRepository.getBookById(bookId);
        Optional<User> user = userRepository.findByUsername(currentUsername);

        if (book.isEmpty() || book.get().getCount() == 0) {
            throw new NoDataFoundException("No active books found");
        }

        if (user.isEmpty()) {
            throw new NoDataFoundException("No such user found");
        }

        BorrowedBook borrowedBook = new BorrowedBook(
                null,
                user.get(),
                book.get(),
                startDate,
                endDate
        );

        borrowedBook.setStatus(BorrowStatus.BORROWED);

               bookRepository.save(new Book(
                book.get().getId(),
                book.get().getTitle(),
                book.get().getGenre(),
                book.get().getLanguage(),
                book.get().getISBN(),
                book.get().getAuthor(),
                book.get().getPublishDate(),
                book.get().getCount() - 1,
                book.get().getPrice(),
                null,
                null
        ));

        return borrowedBookMapper.toDto(borrowedBookRepository.save(borrowedBook));
    }

    public BorrowedBookResponseDto updateBorrow(Long borrow_id, BorrowStatus status) {
        Optional<BorrowedBook> borrowedBook = borrowedBookRepository.getBorrowById(borrow_id);
        if (borrowedBook.isEmpty()) {
            throw new NoDataFoundException("No active Books found");
        }
        borrowedBook.get().setStatus(status);

        return borrowedBookMapper.toDto(borrowedBookRepository.save(borrowedBook.get()));
    }
}
