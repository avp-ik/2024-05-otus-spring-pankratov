package ru.otus.hw.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.hw.domain.model.Document;
import ru.otus.hw.domain.model.DataForDocument;

import java.util.UUID;

@FeignClient(name = "document-maker-service",
        url = "${document-maker-service.url}",
        fallback = DocumentMakerServiceImpl.class)
public interface DocumentMakerService {

    @GetMapping("/api/v1/docs/{id}")
    public ResponseEntity<byte[]> getDocument(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/docs")
    public Document createDocument(@RequestBody @Valid DataForDocument dataForDocument);
}