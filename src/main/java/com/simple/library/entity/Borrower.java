package com.simple.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "borrowed_book_id", referencedColumnName = "id")
    private Book borrowedBook;
}
