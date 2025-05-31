package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.hw.feign.DocumentPrintService;
import ru.otus.hw.feign.DocumentStoreService;
import ru.otus.hw.integration.DocumentPreparationService;
import ru.otus.hw.models.DataForDocument;
import ru.otus.hw.models.Document;
import ru.otus.hw.models.PrintedDocument;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentMakerServiceImpl implements DocumentMakerService {

    final private DocumentStoreService documentStoreService;

    final private DocumentPreparationService documentPreparationService;

    @Override
    public ResponseEntity<byte[]> getDocument(UUID uuid) {

        return documentStoreService.get(uuid);
    }

    @Override
    public Document createDocument(DataForDocument dataForDocument) {

        Collection<DataForDocument> dataForDocuments = List.of(dataForDocument);

        Collection<DataForDocument> resultDataForDocuments = documentPreparationService.runProcess(dataForDocuments);

//        PrintedDocument printedDocument = new PrintedDocument();
//        printedDocument.setName(dataForDocument.getName());
//        printedDocument.setTemplate(dataForDocument.getTemplate());
//        printedDocument.setData(dataForDocument.getData());
//
//        return documentPrintService.create(printedDocument);

        return resultDataForDocuments == null ? null : resultDataForDocuments.stream().findFirst().get().getDocument() ;
    }
}