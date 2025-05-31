package ru.otus.hw.integration;

import ru.otus.hw.models.DataForDocument;
import ru.otus.hw.models.Document;

import java.util.Collection;

public interface DocumentPreparationService {
    DataForDocument transformToPrintedDocument(DataForDocument dataForDocument);

    DataForDocument transformToSignedDocument(DataForDocument dataForDocument);

    Collection<DataForDocument> runProcess(Collection<DataForDocument> dataForDocuments);
}