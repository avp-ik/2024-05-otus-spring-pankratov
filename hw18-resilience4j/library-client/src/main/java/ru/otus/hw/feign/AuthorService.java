package ru.otus.hw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.AuthorDto;

import java.util.List;

@FeignClient(name = "author-service",
        url = "${library-service.url}",
        fallback = AuthorServiceImpl.class)
public interface AuthorService {
    @GetMapping("/api/v1/authors")
    List<AuthorDto> findAll();
}