package com.simple.library.service;

import com.simple.library.dto.BookDTO;
import com.simple.library.entity.Book;
import com.simple.library.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    @Order(1)
    void getBooks() {
        bookRepository.saveAll(
                List.of(
                        Book.builder().title("Test Book 1").author("Test Author").isbn("1-1-1").build(),
                        Book.builder().title("Test Book 2").author("Test Author").isbn("1-1-2").build()
                )

        );
        List<BookDTO> books = bookService.getBooks();
        assertEquals(2, books.size());
        assertTrue(books.stream().map(BookDTO::getTitle).anyMatch(s -> s.equals("Test Book 1")));
        assertTrue(books.stream().map(BookDTO::getTitle).anyMatch(s -> s.equals("Test Book 2")));
        assertTrue(books.stream().map(BookDTO::getAuthor).anyMatch(s -> s.equals("Test Author")));
    }

    @Test
    @Order(2)
    void registerNewBookWhenSameISBNAndDifferentTitle() {
        BookDTO bookDTO = BookDTO.builder().title("Test Book").author("Test Author").isbn("1-1-1").build();

        ResponseStatusException exception = assertThrowsExactly(ResponseStatusException.class, () -> bookService.registerNewBook(bookDTO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("For given ISBN : 1-1-1 Book's Title and Author should be, Title : Test Book 1 and Author : Test Author", exception.getReason());
    }

    @Test
    @Order(2)
    void registerNewBookWhenSameISBNAndDifferentAuthor() {
        BookDTO bookDTO = BookDTO.builder().title("Test Book 1").author("Test1 Author").isbn("1-1-1").build();

        ResponseStatusException exception = assertThrowsExactly(ResponseStatusException.class, () -> bookService.registerNewBook(bookDTO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("For given ISBN : 1-1-1 Book's Title and Author should be, Title : Test Book 1 and Author : Test Author", exception.getReason());
    }

    @Test
    @Order(3)
    void registerNewBookWhenSameISBN() {
        BookDTO bookDTO = BookDTO.builder().title("Test Book 1").author("Test Author").isbn("1-1-1").build();

        bookService.registerNewBook(bookDTO);
        List<Book> books = bookRepository.findAll();

        assertEquals(3, books.size());
    }

    @Test
    @Order(4)
    void registerNewBookWhenDifferentISBN() {
        BookDTO bookDTO = BookDTO.builder().title("Test Book 3").author("Test Author").isbn("1-1-3").build();

        bookService.registerNewBook(bookDTO);
        List<Book> books = bookRepository.findAll();

        assertEquals(4, books.size());
    }

}

