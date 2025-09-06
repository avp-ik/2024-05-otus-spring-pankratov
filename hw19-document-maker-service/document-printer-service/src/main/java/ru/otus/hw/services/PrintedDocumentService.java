package ru.otus.hw.services;

import org.springframework.http.ResponseEntity;
import ru.otus.hw.models.Document;
import ru.otus.hw.models.PrintedDocument;

import java.util.UUID;

public interface PrintedDocumentService {

    ResponseEntity<byte[]> get(UUID id);

    Document create(PrintedDocument printedDocument);

}
