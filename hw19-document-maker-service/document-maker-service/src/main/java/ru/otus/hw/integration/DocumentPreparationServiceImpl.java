package ru.otus.hw.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.feign.DocumentPrintService;
import ru.otus.hw.models.DataForDocument;
import ru.otus.hw.models.PrintedDocument;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentPreparationServiceImpl implements DocumentPreparationService {

    private final DocumentPreparationGateway documentPreparationGateway;

    private final DocumentPrintService documentPrintService;

    @Override
    public DataForDocument transformToPrintedDocument(DataForDocument dataForDocument) {
        log.info("Transform the data to printed document for {}...", dataForDocument.getName());

        PrintedDocument printedDocument = new PrintedDocument();
        printedDocument.setName(dataForDocument.getName());
        printedDocument.setTemplate(dataForDocument.getTemplate());
        printedDocument.setData(dataForDocument.getData());

        dataForDocument.setDocument(documentPrintService.create(printedDocument));

        log.info("The printed document was created fot {}.", dataForDocument.getName());

        return dataForDocument;
    }

    @Override
    public DataForDocument transformToSignedDocument(DataForDocument dataForDocument) {
        log.info("Transform the data to signed document for {}...", dataForDocument.getName());

        // Not implemented yet.

        log.info("The signed document was created fot {}.", dataForDocument.getName());

        return dataForDocument;
    }

    @Override
    public DataForDocument transformToChangedDocument(DataForDocument dataForDocument) {
        log.info("Transform the data to changed document for {}...", dataForDocument.getName());

        // Not implemented yet.

        log.info("The changed document was created fot {}.", dataForDocument.getName());

        return dataForDocument;
    }

    public Collection<DataForDocument> runProcess(Collection<DataForDocument> dataForDocuments) {

        Collection<DataForDocument> resultDataForDocuments = documentPreparationGateway.process(dataForDocuments);

        log.info(resultDataForDocuments.toString());

        return resultDataForDocuments;
    }
}
