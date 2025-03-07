package com.hd.vbookstore.core.configs;

import com.hd.vbookstore.commons.BookDto;
import com.hd.vbookstore.core.utils.BookMapper;
import com.hd.vbookstore.data.UserRepository;
import com.hd.vbookstore.domain.Book;
import com.hd.vbookstore.domain.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
public class ApplicationConfigurations {

    @Autowired
    private final UserRepository userRepository;

    public ApplicationConfigurations(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> {
                    if (user.getRoles().isEmpty()) {

                        user.addRole(Role.ROLE_USER);
                    }
                    return user;
                })
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + username));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public BookMapper bookMapper() {

        return new BookMapper() {
            @Override
            public BookDto bookDtoFromBook(Book book) {
                if (book == null) return null;

                BookDto bookDto = new BookDto();
                bookDto.setId(book.getId());
                bookDto.setTitle(book.getTitle());
                bookDto.setGenre(book.getGenre());
                bookDto.setLanguage(book.getLanguage());
                bookDto.setISBN(book.getISBN());
                bookDto.setAuthor(book.getAuthor());
                bookDto.setPublishDate(book.getPublishDate());
                bookDto.setCount(book.getCount());
                bookDto.setPrice(book.getPrice());

                return bookDto;
            }

            @Override
            public Book bookFromBookDto(BookDto bookDto) {
                if (bookDto == null) return null;

                return new Book(
                        bookDto.getId(),
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
            }
        };
    }


    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }

        @ExceptionHandler(RedisConnectionFailureException.class)
        public ResponseEntity<String> handleRedisConnectionFailure(RedisConnectionFailureException ex) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Redis service is currently unavailable");
        }
    }
}
