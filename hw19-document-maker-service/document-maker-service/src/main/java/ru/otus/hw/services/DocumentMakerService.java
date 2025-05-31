package ru.otus.hw.services;

import org.springframework.http.ResponseEntity;
import ru.otus.hw.models.DataForDocument;
import ru.otus.hw.models.Document;

import java.util.UUID;

public interface DocumentMakerService {
    ResponseEntity<byte[]> getDocument(UUID uuid);

    Document createDocument(DataForDocument dataForDocument);
}