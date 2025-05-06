package ru.otus.hw.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.Butterfly;
import ru.otus.hw.models.Egg;

import java.util.Collection;

@MessagingGateway
public interface ButterflyIncubatorGateway {
    @Gateway(requestChannel = "eggChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Egg> eggs);
}