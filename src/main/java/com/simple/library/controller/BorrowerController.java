package com.simple.library.controller;

import com.simple.library.dto.BorrowerDTO;
import com.simple.library.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrower")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @PostMapping("/register")
    public ResponseEntity<BorrowerDTO> registerNewBorrower(@RequestBody @Valid BorrowerDTO borrowerDTO) {
        BorrowerDTO savedBorrowerDTO = borrowerService.registerNewBorrower(borrowerDTO);
        return new ResponseEntity<>(savedBorrowerDTO, HttpStatus.CREATED);
    }

    @PostMapping("/borrow/{borrowerId}/{bookId}")
    public ResponseEntity<BorrowerDTO> borrowABook(@PathVariable("borrowerId") Long borrowerId, @PathVariable("bookId") Long bookId) {
        BorrowerDTO borrowerDTO = borrowerService.borrowBook(borrowerId, bookId);
        return new ResponseEntity<>(borrowerDTO, HttpStatus.OK);
    }

    @PostMapping("/return/{borrowerId}/{bookId}")
    public ResponseEntity<BorrowerDTO> returnBook(@PathVariable("borrowerId") Long borrowerId, @PathVariable("bookId") Long bookId) {
        BorrowerDTO borrowerDTO = borrowerService.returnBook(borrowerId, bookId);
        return new ResponseEntity<>(borrowerDTO, HttpStatus.OK);
    }

}