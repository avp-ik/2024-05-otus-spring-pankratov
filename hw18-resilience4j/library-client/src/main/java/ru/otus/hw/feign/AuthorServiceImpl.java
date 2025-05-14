package ru.otus.hw.feign;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;

import java.util.List;

@Component
public class AuthorServiceImpl implements AuthorService {

    @Override
    public List<AuthorDto> findAll() {
        return List.of(new AuthorDto(0L, ""));
    }
}