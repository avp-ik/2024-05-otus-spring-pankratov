package ru.otus.hw.feign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.model.DataForDocument;
import ru.otus.hw.domain.model.Document;

import java.util.UUID;

@Component
public class DocumentMakerServiceImpl implements DocumentMakerService {
    @Override
    public ResponseEntity<byte[]> getDocument(UUID id)  {
        return new ResponseEntity<byte[]>(null, null, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public Document createDocument(DataForDocument dataForDocument) {
        return new Document();
    }
}
