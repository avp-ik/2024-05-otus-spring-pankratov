package ru.otus.hw.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.hw.models.Document;

import java.util.UUID;

@FeignClient(name = "document-store-service",
        url = "${document-store-service.url}")
public interface DocumentStoreService {

    @GetMapping("/api/v1/docs/{id}")
    public ResponseEntity<byte[]> get(@PathVariable("id") UUID id);

    @PostMapping(value = "/api/v1/docs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> upload(@RequestPart("file") MultipartFile file);

}