package ru.otus.hw.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.integration.DocumentPreparationService;

@Configuration
public class DocumentPreparationIntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> documentPreparationChannel() { return MessageChannels.queue(5); }

    @Bean
    public MessageChannelSpec<?, ?> documentChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(1000).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow documentPreparationFlow(DocumentPreparationService documentPreparationService) {
        return IntegrationFlow
                .from(documentPreparationChannel())
                .split()
                .handle(documentPreparationService, "transformToPrintedDocument")
                .handle(documentPreparationService, "transformToSignedDocument")
                .aggregate()
                .channel(documentChannel())
                .get();
    }
}