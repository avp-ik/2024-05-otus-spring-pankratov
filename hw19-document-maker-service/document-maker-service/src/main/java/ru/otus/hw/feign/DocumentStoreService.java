package ru.otus.hw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "document-store-service",
        url = "${document-store-service.url}")
public interface DocumentStoreService {

    @GetMapping("/api/v1/docs/{id}")
    public ResponseEntity<byte[]> get(@PathVariable("id") UUID id);

}