package ru.otus.hw.service;

import ru.otus.hw.models.Butterfly;
import ru.otus.hw.models.Caterpillar;
import ru.otus.hw.models.Chrysalis;
import ru.otus.hw.models.Egg;

import java.util.Collection;

public interface IncubatorService {
    Caterpillar transformToCaterpillar(Egg egg);

    Chrysalis transformToChrysalis(Caterpillar caterpillar);

    Butterfly transformToButterfly(Chrysalis chrysalis);

    Collection<Butterfly> runProcess(Collection<Egg> eggs);
}