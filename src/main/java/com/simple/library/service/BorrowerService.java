package com.simple.library.service;

import com.simple.library.dto.BorrowerDTO;
import com.simple.library.entity.Book;
import com.simple.library.entity.Borrower;
import com.simple.library.repository.BookRepository;
import com.simple.library.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    public BorrowerDTO registerNewBorrower(BorrowerDTO borrowerDTO) {
        Borrower borrower = mapToBorrower(borrowerDTO);
        return mapToBorrowerDTO(borrowerRepository.save(borrower));
    }


    @Transactional
    public BorrowerDTO borrowBook(Long borrowerId, Long bookId) {
        Optional<Borrower> optionalBorrower = borrowerRepository.findById(borrowerId);
        if (optionalBorrower.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrower Not Found!");

        Borrower borrower = optionalBorrower.get();
        if (borrower.getBorrowedBook() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.join(" ", "You have already borrowed,", borrower.getBorrowedBook().getTitle()));

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found!");

        Book book = optionalBook.get();
        if (!book.isAvailable())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This book is already borrowed!");

        borrower.setBorrowedBook(book);
        book.setAvailable(false);

        bookRepository.save(book);
        return mapToBorrowerDTO(borrowerRepository.save(borrower));
    }

    @Transactional
    public BorrowerDTO returnBook(Long borrowerId, Long bookId) {
        Optional<Borrower> optionalBorrower = borrowerRepository.findById(borrowerId);
        if (optionalBorrower.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Borrower Not Found!");

        Borrower borrower = optionalBorrower.get();

        if (borrower.getBorrowedBook() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You haven't borrowed a book yet!");

        if (!Objects.equals(borrower.getBorrowedBook().getId(), bookId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.join(" ", "You have borrowed a different book,", borrower.getBorrowedBook().getTitle()));

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Not Found!");

        Book book = optionalBook.get();
        borrower.setBorrowedBook(null);
        book.setAvailable(true);

        bookRepository.save(book);
        return mapToBorrowerDTO(borrowerRepository.save(borrower));
    }

    private Borrower mapToBorrower(BorrowerDTO borrowerDTO) {
        return Borrower.builder()
                .name(borrowerDTO.getName())
                .email(borrowerDTO.getEmail())
                .build();
    }

    private BorrowerDTO mapToBorrowerDTO(Borrower borrower) {
        return BorrowerDTO.builder()
                .id(borrower.getId())
                .name(borrower.getName())
                .email(borrower.getEmail())
                .borrowedBookId(borrower.getBorrowedBook() != null ? borrower.getBorrowedBook().getId() : null)
                .build();
    }

}
