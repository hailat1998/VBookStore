package com.hd.vbookstore.app;


import com.hd.vbookstore.data.BookRepository;
import com.hd.vbookstore.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@Profile("!prod")
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            if (bookRepository.count() == 0) {
                List<Book> books = List.of(
                        new Book(
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
                        ),
                        new Book(
                                null,
                                "1984",
                                "Science Fiction",
                                "English",
                                "978-0451524935",
                                "George Orwell",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1949-06-08"),
                                3,
                                43.0f,
                                null,
                                null
                        ),
                        new Book(
                                null,
                                "Pride and Prejudice",
                                "Romance",
                                "English",
                                "978-0141439518",
                                "Jane Austen",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1813-01-28"),
                                4,
                                54.3f,
                                null,
                                null
                        ),
                        new Book(
                                null,
                                "One Hundred Years of Solitude",
                                "Magical Realism",
                                "Spanish",
                                "978-0060883287",
                                "Gabriel García Márquez",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1967-05-30"),
                                2,
                                65.0f,
                                null,
                                null
                        ),
                        new Book(
                                null,
                                "The Hobbit",
                                "Fantasy",
                                "English",
                                "978-0547928227",
                                "J.R.R. Tolkien",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1937-09-21"),
                                6,
                                43.9f,
                                null,
                                null
                           ),
                        new Book(
                                null,
                                "Crime and Punishment",
                                "Psychological Fiction",
                                "Russian",
                                "978-0143107637",
                                "Fyodor Dostoevsky",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1866-01-01"),
                                3,
                                54.9f,
                                null,
                                null
                            ),
                        new Book(
                                null,
                                "The Catcher in the Rye",
                                "Literary Fiction",
                                "English",
                                "978-0316769488",
                                "J.D. Salinger",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1951-07-16"),
                                4,
                                32.9f,
                                null,
                                null
                            ),
                        new Book(
                                null,
                                "Don Quixote",
                                "Adventure",
                                "Spanish",
                                "978-0142437230",
                                "Miguel de Cervantes",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1605-01-16"),
                                2,
                                65.0f,
                                null,
                                null
                            ),
                        new Book(
                                null,
                                "The Divine Comedy",
                                "Poetry",
                                "Italian",
                                "978-0142437223",
                                "Dante Alighieri",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1320-01-01"),
                                1,
                                23.0f,
                                null,
                                null
                           ),
                        new Book(
                                null,
                                "War and Peace",
                                "Historical Fiction",
                                "Russian",
                                "978-1400079988",
                                "Leo Tolstoy",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1869-01-01"),
                                3,
                                84.0f,
                                null,
                                null
                             ),
                        new Book(
                                null,
                                "War and Peace - 2",
                                "Historical Fiction",
                                "Russian",
                                "978-1400079988",
                                "Leo Tolstoy",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1869-01-01"),
                                8,
                                23.0f,
                                null,
                                null
                        ),
                        new Book(
                                null,
                                "War and Peace - 3",
                                "Historical Fiction",
                                "Russian",
                                "978-1400079988",
                                "Leo Tolstoy",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1869-01-01"),
                                9,
                                12.0f,
                                null,
                                null
                        )
                   );


                bookRepository.saveAll(books);
                log.info("Database has been populated with the following books:");
                bookRepository.findAll().forEach(book ->
                        log.info(book + " by " + book.getISBN()));
            } else {
                log.info("Database already contains data, skipping initialization");
            }
        };
    }
}
