package com.hd.vbookstore.api;

import com.hd.vbookstore.commons.BookDto;
import com.hd.vbookstore.core.services.BookService;
import com.hd.vbookstore.commons.AuthorBookCountDTO;
import com.hd.vbookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/book", produces="application/json")
public class BookController {

    public final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
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

        Page<Book> books = bookService.getBooks(pageNumber, pageSize, sortBy, direction );

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
        return bookService.getById(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/save", consumes="application/json")
    public Book postBook(@RequestBody BookDto book) {
        return bookService.save(book);
    }


    @GetMapping("/search")
    ResponseEntity<Page<Book>> search(@RequestParam String searchTerm,  @RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        try {

            Page<Book> books = bookService.search(searchTerm, pageNumber);

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
    ResponseEntity<AuthorBookCountDTO>  countByAuthor(@RequestParam(name = "author") String author) {
        Long count = bookService.countByAuthor(author);
       AuthorBookCountDTO result = new AuthorBookCountDTO(author, count);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/countByAuthors")
    ResponseEntity<List<AuthorBookCountDTO>> countByAuthors() {
        List<AuthorBookCountDTO> counts = bookService.getAllAuthorsBookCounts();
        return ResponseEntity.ok(counts);
       }
}


