package com.simple.library.service;

import com.simple.library.dto.BorrowerDTO;
import com.simple.library.entity.Borrower;
import com.simple.library.repository.BorrowerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public class BorrowerServiceTest {

    @Autowired
    BorrowerService borrowerService;

    @Autowired
    BorrowerRepository borrowerRepository;

    @Test
    @Order(1)
    void registerBorrower() {
        borrowerService.registerNewBorrower(BorrowerDTO.builder().name("Test User").email("user@gmail.com").build());
        List<Borrower> borrowerList = borrowerRepository.findAll();

        assertEquals(1, borrowerList.size());
        assertEquals("Test User", borrowerList.get(0).getName());
    }

    @Test
    @Order(1)
    void borrowBookShouldThrowExceptionWhenBorrowerNotFound() {
        ResponseStatusException exception = assertThrowsExactly(ResponseStatusException.class, () -> borrowerService.borrowBook(1L,1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Borrower Not Found!", exception.getReason());
    }

    @Test
    @Order(2)
    void borrowBookShouldThrowExceptionWhenBookNotFound() {
        ResponseStatusException exception = assertThrowsExactly(ResponseStatusException.class, () -> borrowerService.borrowBook(1L,1L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Book Not Found!", exception.getReason());
    }

}
