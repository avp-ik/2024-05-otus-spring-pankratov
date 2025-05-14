package ru.otus.hw.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.feign.AuthorServiceImpl;

import java.util.List;

@FeignClient(name = "author-service",
        url = "${library-service.url}",
        fallback = AuthorServiceImpl.class)
public interface AuthorService {
    @CircuitBreaker(name = "findAll")
    @GetMapping("/api/v1/authors")
    List<AuthorDto> findAll();
}