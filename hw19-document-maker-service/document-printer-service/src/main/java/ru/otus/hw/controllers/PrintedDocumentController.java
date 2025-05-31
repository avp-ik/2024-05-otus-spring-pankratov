package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.models.Document;
import ru.otus.hw.models.PrintedDocument;
import ru.otus.hw.services.DocumentStoreService;
import ru.otus.hw.services.PrintedDocumentService;

import java.security.Principal;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/printedDocs")
public class PrintedDocumentController {

    private final PrintedDocumentService printedDocumentService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPrintedDocument(@PathVariable("id") UUID id) {

        return printedDocumentService.get(id);
        //{"id":null,"name":"71d5fa8e-f3c9-47e7-9a91-44ea72ed2972","template":"base_form.html","data":{"from":"Masha","to":"Sasha"},"url":"https://localhost:8080/api/v1/docs/71d5fa8e-f3c9-47e7-9a91-44ea72ed2972"}
    }

    @PostMapping()
    public Document createPrintedDocument(@RequestBody @Valid PrintedDocument printedDocument) {
        return printedDocumentService.create(printedDocument);
    }
}