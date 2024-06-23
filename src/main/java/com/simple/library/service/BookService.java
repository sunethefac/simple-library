package com.simple.library.service;

import com.simple.library.dto.BookDTO;
import com.simple.library.entity.Book;
import com.simple.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDTO registerNewBook(BookDTO bookDTO) throws ResponseStatusException {
        Optional<Book> bookWithSameIsbn = bookRepository.findFirstByIsbn(bookDTO.getIsbn());
        if (bookWithSameIsbn.isPresent()
                && (!bookWithSameIsbn.get().getTitle().equals(bookDTO.getTitle())
                    || !bookWithSameIsbn.get().getAuthor().equals(bookDTO.getAuthor()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.join(" ","For given ISBN :", bookDTO.getIsbn(), "Book's Title and Author should be,",
                                                "Title :", bookWithSameIsbn.get().getTitle(), "and Author :", bookWithSameIsbn.get().getAuthor()));
        }

        return mapToBookDTO(bookRepository.save(mapToBook(bookDTO)));
    }

    public List<BookDTO> getBooks() {
        return bookRepository.findAll().stream().map(this::mapToBookDTO).toList();
    }

    private Book mapToBook(BookDTO bookDTO) {
        return Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .isbn(bookDTO.getIsbn())
                .available(true)
                .build();
    }

    private BookDTO mapToBookDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .available(book.isAvailable())
                .build();
    }

}
