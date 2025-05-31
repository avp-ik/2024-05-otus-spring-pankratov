package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.hw.feign.DocumentStoreService;
import ru.otus.hw.integration.DocumentPreparationService;
import ru.otus.hw.models.DataForDocument;
import ru.otus.hw.models.Document;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentMakerServiceImpl implements DocumentMakerService {

    private final DocumentStoreService documentStoreService;

    private final DocumentPreparationService documentPreparationService;

    @Override
    public ResponseEntity<byte[]> getDocument(UUID uuid) {

        return documentStoreService.get(uuid);
    }

    @Override
    public Document createDocument(DataForDocument dataForDocument) {

        Collection<DataForDocument> dataForDocuments = List.of(dataForDocument);

        Collection<DataForDocument> resultDataForDocuments = documentPreparationService.runProcess(dataForDocuments);

        return resultDataForDocuments == null ? null : resultDataForDocuments.stream().findFirst().get().getDocument() ;
    }
}