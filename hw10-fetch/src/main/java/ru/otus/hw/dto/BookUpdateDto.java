package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {
    private long id;

    @NotBlank(message = "{title-field-must-not-be-blank}")
    private String title;

    private long authorId;

    private long genreId;
}