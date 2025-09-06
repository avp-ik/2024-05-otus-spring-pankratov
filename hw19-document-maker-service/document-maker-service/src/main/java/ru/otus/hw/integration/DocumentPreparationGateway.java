package ru.otus.hw.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.DataForDocument;

import java.util.Collection;

@MessagingGateway
public interface DocumentPreparationGateway {
    @Gateway(requestChannel = "documentPreparationChannel",
            replyChannel = "documentChannel")
    Collection<DataForDocument> process(Collection<DataForDocument> dataForDocuments);
}