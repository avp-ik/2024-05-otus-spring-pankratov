package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {
    @NotBlank
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String authorId;

    @NotBlank
    private String genreId;
}