package ru.otus.hw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.domain.model.DataForDocument;
import ru.otus.hw.domain.model.Document;
import ru.otus.hw.feign.DocumentMakerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/docs")
@RequiredArgsConstructor
@Tag(name = "Работа с документами")
public class DocumentMakerController {
    private final DocumentMakerService documentMakerService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение документа")
    public ResponseEntity<byte[]> getDocument(@PathVariable("id") UUID id) {
        return documentMakerService.getDocument(id);
    }

    @PostMapping()
    @Operation(summary = "Создание документа")
    public Document createDocument(@RequestBody @Valid DataForDocument dataForDocument) {
        return documentMakerService.createDocument(dataForDocument);
    }
}