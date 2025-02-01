package com.hd.vbookstore.api;

import com.hd.vbookstore.data.BookRepository;
import com.hd.vbookstore.data.mapper.AuthorBookCountDTO;
import com.hd.vbookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/book", produces="application/json")
public class BookController {

    public final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Book>> getBooks(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", defaultValue = "20") int pageSize,
            @RequestParam(name = "sort", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction) {

        try {

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


            Page<Book> books = bookRepository.findAll(pageRequest);

            return ResponseEntity.ok(books);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid pagination parameters",
                    e
            );
        }
    }

    @GetMapping("/{id}")
    public Optional<Book> getById(@PathVariable Long id) {
        return bookRepository.findById(id);
    }

    @PostMapping(consumes="application/json")
    public Book postBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }


    @GetMapping("/search")
    ResponseEntity<Page<Book>> search(@RequestParam String searchTerm,  @RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        try {
            Sort.Direction sortDirection = Sort.Direction.ASC ;
            PageRequest pageRequest = PageRequest.of(
                     pageNumber ,
                    10,
                    Sort.by(sortDirection, "createdAt")
            );

            Page<Book> books = bookRepository.searchBooks(searchTerm, pageRequest);


            return ResponseEntity.ok(books);

        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid pagination parameters",
                    e
            );
        }
    }

    @GetMapping("/countByAuthor")
    ResponseEntity<?> countByAuthor(@RequestParam(name = "author", required = false) String author) {
        if (author == null || author.isEmpty()) {
            List<AuthorBookCountDTO> counts = bookRepository.getAllAuthorsBookCounts();
            return ResponseEntity.ok(counts);
        } else {
            Long count = bookRepository.countBooksByAuthor(author);
            List<AuthorBookCountDTO> result = List.of(new AuthorBookCountDTO(author, count));
            return ResponseEntity.ok(result);
        }
    }
}
