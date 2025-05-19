package ru.otus.hw.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.service.IncubatorService;

@Configuration
public class IncubatorIntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> eggChannel() {
        return MessageChannels.queue(5);
    }

    @Bean
    public MessageChannelSpec<?, ?> caterpillarChannel() {
        return MessageChannels.queue(5);
    }

    @Bean
    public MessageChannelSpec<?, ?> chrysalisChannel() {
        return MessageChannels.queue(5);
    }

    @Bean
    public MessageChannelSpec<?, ?> butterflyChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(1000).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow butterflyFlow(IncubatorService incubatorService) {
        return IntegrationFlow.from(eggChannel())
                .split()
                .handle(incubatorService, "transformToCaterpillar")
                .aggregate()
                .channel(caterpillarChannel())
                .split()
                .handle(incubatorService, "transformToChrysalis")
                .aggregate()
                .channel(chrysalisChannel())
                .split()
                .handle(incubatorService, "transformToButterfly")
                .aggregate()
                .channel(butterflyChannel())
                .get();
    }
}