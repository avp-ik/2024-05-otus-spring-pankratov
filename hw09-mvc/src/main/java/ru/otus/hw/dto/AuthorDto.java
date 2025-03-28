package ru.otus.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Автор")
public class AuthorDto {
    private long id;

    @NotBlank(message = "{fullname-field-should-not-be-blank}")
    private String fullName;
}