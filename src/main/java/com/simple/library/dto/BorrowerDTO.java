package com.simple.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowerDTO {

    private Long id;
    @NotEmpty(message = "Name should not be empty!")
    private String name;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Email should be in correct format!")
    private String email;
    private Long borrowedBookId;

}
