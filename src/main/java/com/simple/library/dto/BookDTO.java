package com.simple.library.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {

    private Long id;
    @NotEmpty(message = "ISBN should not be empty!")
    private String isbn;
    @NotEmpty(message = "Book title should not be empty!")
    private String title;
    @NotEmpty(message = "Author name should not be empty!")
    private String author;
    private boolean available;

}
