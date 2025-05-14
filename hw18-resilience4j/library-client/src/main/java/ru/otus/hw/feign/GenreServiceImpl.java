package ru.otus.hw.feign;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.GenreDto;

import java.util.List;

@Component
public class GenreServiceImpl implements GenreService {

    @Override
    public List<GenreDto> findAll() {
        return List.of(new GenreDto(0L, ""));
    }
}