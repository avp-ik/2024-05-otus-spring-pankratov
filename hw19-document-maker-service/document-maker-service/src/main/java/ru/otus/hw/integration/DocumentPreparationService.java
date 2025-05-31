package ru.otus.hw.integration;

import ru.otus.hw.models.DataForDocument;
import java.util.Collection;

public interface DocumentPreparationService {
    DataForDocument transformToPrintedDocument(DataForDocument dataForDocument);

    DataForDocument transformToSignedDocument(DataForDocument dataForDocument);

    DataForDocument transformToChangedDocument(DataForDocument dataForDocument);

    Collection<DataForDocument> runProcess(Collection<DataForDocument> dataForDocuments);
}