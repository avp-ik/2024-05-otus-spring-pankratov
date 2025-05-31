package ru.otus.hw.services;

import ru.otus.hw.models.Document;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface DocumentService {
    Document get(UUID uuid) throws IOException, NoSuchAlgorithmException;

    Document put(Document document) throws IOException, NoSuchAlgorithmException;
}