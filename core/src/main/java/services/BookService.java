package com.hd.vbookstore.core.services;


import com.hd.vbookstore.commons.BookDto;
import com.hd.vbookstore.core.utils.BookMapper;
import com.hd.vbookstore.data.BookRepository;
import com.hd.vbookstore.commons.AuthorBookCountDTO;
import com.hd.vbookstore.domain.Book;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;


    BookService(BookRepository bookRepository,
               BookMapper bookMapper
               ) {
        this.bookRepository = bookRepository ;
        this.bookMapper = bookMapper;
    }

    @Cacheable(value = "booksCache", key = "'page:' + #pageNumber + ':size:' + #pageSize")
    public Page<BookDto> getBooks(int pageNumber, int pageSize, String sortBy, String direction) {
        if (pageSize > 100) {
            pageSize = 100;
        }

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(sortDirection, sortBy)
        );

      return bookRepository.findAll(pageRequest).map(bookMapper::bookDtoFromBook);
    }

    @Cacheable(value = "bookCache", key = "#id")
    public Optional<BookDto> getById(Long id) {
        return bookRepository.findById(id).map(bookMapper::bookDtoFromBook);
    }

    public BookDto save(BookDto bookDto) {
        Book book = new Book(
                null,
                bookDto.getTitle(),
                bookDto.getGenre(),
                bookDto.getLanguage(),
                bookDto.getISBN(),
                bookDto.getAuthor(),
                bookDto.getPublishDate(),
                bookDto.getCount(),
                bookDto.getPrice(),
                null,
                null
             );

        return bookMapper.bookDtoFromBook(bookRepository.save(book));
    }

    @Cacheable(value = "searchCache", key = "'searchTerm:' + #searchTerm + 'pageNumber:' + #pageNumber")
    public Page<BookDto> search(String searchTerm, int pageNumber) {
        Sort.Direction sortDirection = Sort.Direction.ASC ;
        PageRequest pageRequest = PageRequest.of(
                pageNumber ,
                10,
                Sort.by(sortDirection, "createdAt")
        );

        return bookRepository.searchBooks(searchTerm, pageRequest).map(bookMapper::bookDtoFromBook);
    }

    @Cacheable(value = "authorsCache", key = "'allAuthors'")
    public List<AuthorBookCountDTO> getAllAuthorsBookCounts(){
        return bookRepository.getAllAuthorsBookCounts();
    }

    @Cacheable(value = "authorCache", key = "#author")
    public Long countByAuthor(String author) {
        return bookRepository.countBooksByAuthor(author);
    }
}
