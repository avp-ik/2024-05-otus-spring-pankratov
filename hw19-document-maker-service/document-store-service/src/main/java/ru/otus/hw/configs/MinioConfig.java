package ru.otus.hw.configs;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class MinioConfig
{
    @Value("${endpoint}")
    public String endpoint;

    @Value("${bucketName}")
    public String bucketName = "2024-05-otus-spring-pankratov";

    @Value("${accessKey}")
    public String accessKey = "Q3AM3UQ867SPQQA43P2F";

    @Value("${secretKey}")
    public String secretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";
}
