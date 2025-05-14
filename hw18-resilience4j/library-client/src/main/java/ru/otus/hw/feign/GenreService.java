package ru.otus.hw.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.GenreDto;

import java.util.List;

@FeignClient(name = "genre-service",
        url = "${library-service.url}",
        fallback = GenreServiceImpl.class)
public interface GenreService {
    @CircuitBreaker(name = "findAll")
    @GetMapping("/api/v1/genres")
    List<GenreDto> findAll();
}