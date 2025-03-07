package com.hd.vbookstore.app.repository;

import com.hd.vbookstore.data.BookRepository;
import com.hd.vbookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {
        TestConfig.class,
        BookRepository.class
})
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testSaveBook() throws ParseException {
        Book book = new Book(
                null,
                "The Great Gatsby",
                "Fiction",
                "English",
                "978-0743273565",
                "F. Scott Fitzgerald",
                new SimpleDateFormat("yyyy-MM-dd").parse("1925-04-10"),
                5,
                30.4f,
                null,
                null
        ) ;

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }

}
