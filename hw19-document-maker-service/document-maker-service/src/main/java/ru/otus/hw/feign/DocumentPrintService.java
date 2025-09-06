package ru.otus.hw.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.models.Document;
import ru.otus.hw.models.PrintedDocument;

import java.util.UUID;

@FeignClient(name = "document-print-service",
        url = "${document-print-service.url}")
public interface DocumentPrintService {

    @GetMapping("/api/v1/docs/{id}")
    public ResponseEntity<byte[]> get(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/printedDocs")
    public Document create(@RequestBody @Valid PrintedDocument printedDocument);

}