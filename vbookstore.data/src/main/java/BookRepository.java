package com.hd.vbookstore.data;

import com.hd.vbookstore.commons.AuthorBookCountDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hd.vbookstore.domain.Book;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Instead of findById, use getBookById to avoid conflict
    @Query("SELECT b FROM Book b WHERE b.id = :id")
    Optional<Book> getBookById(@Param("id") Long id);

    // Override the existing findById to maintain compatibility
    @NotNull
    @Override
    Optional<Book> findById(@NotNull Long id);

    // Keep these methods as they are used in other parts
    @NotNull
    @Override
    List<Book> findAll();

//    @NotNull
//    @Override
//    @Transactional
//    <S extends Book> S save(@NotNull S book);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByAuthorContainingIgnoreCase(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')) " +
            "AND LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> findByTitleAndAuthorContainingIgnoreCase(
            @Param("title") String title,
            @Param("author") String author
    );

    @Query("SELECT b FROM Book b WHERE b.publishDate >= :date")
    List<Book> findByPublishDateAfter(@Param("date") Date date);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.author = :author")
    Long countBooksByAuthor(@Param("author") String author);

    @Query("SELECT new com.hd.vbookstore.commons.AuthorBookCountDTO(b.author, COUNT(b)) FROM Book b GROUP BY b.author ORDER BY COUNT(b) DESC")
    List<AuthorBookCountDTO> getAllAuthorsBookCounts();




    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Book> searchBooks(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.author = :author")
    void deleteByAuthor(@Param("author") String author);

    default void saveAll(List<Book> books) {
        books.forEach(this::save);
    }

    @Override
    long count();

}

