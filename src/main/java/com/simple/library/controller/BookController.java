package com.simple.library.controller;

import com.simple.library.dto.BookDTO;
import com.simple.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/register")
    public ResponseEntity<BookDTO> registerNewBook(@RequestBody @Valid BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.registerNewBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public List<BookDTO> getBooks() {
        return bookService.getBooks();
    }

}
