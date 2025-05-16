package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Butterfly;
import ru.otus.hw.models.Caterpillar;
import ru.otus.hw.models.Chrysalis;
import ru.otus.hw.models.Egg;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncubatorServiceImpl implements IncubatorService {

    private final ButterflyIncubatorGateway butterflyIncubatorGateway;

    @Override
    public Caterpillar transformToCaterpillar(Egg egg) {
        log.info("Transform the egg of {} to caterpillar...", egg.getName());
        Caterpillar caterpillar = new Caterpillar(egg.getName());
        delay();
        log.info("The caterpillar of {} has hatched!", caterpillar.getName());

        return caterpillar;
    }

    @Override
    public Chrysalis transformToChrysalis(Caterpillar caterpillar) {
        log.info("The caterpillar {} is turning to chrysalis...", caterpillar.getName());
        Chrysalis chrysalis = new Chrysalis(caterpillar.getName());
        delay();
        log.info("The chrysalis of {} has formed!", chrysalis.getName());

        return chrysalis;
    }

    @Override
    public Butterfly transformToButterfly(Chrysalis chrysalis) {
        log.info("The butterfly {} is hatching from chrysalis...", chrysalis.getName());
        Butterfly butterfly = new Butterfly(chrysalis.getName());
        delay();
        log.info("CONGRATS!!! The butterfly {} has hatched!", butterfly.getName());

        return butterfly;
    }

    private static void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Butterfly> runProcess(Collection<Egg> eggs) {

        Collection<Butterfly> butterflies = butterflyIncubatorGateway.process(eggs);
        log.info(butterflies.toString());

        return butterflies;
    }
}
