package ru.otus.hw.configs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class MinioConfig {
    @Value("${endpoint}")
    private String endpoint;

    @Value("${bucketName}")
    private String bucketName;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;
}
