package ru.otus.hw.services;

import ru.otus.hw.models.Document;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public interface DocumentService {
    Document get(UUID uuid) throws IOException, NoSuchAlgorithmException, InvalidKeyException;

    Document put(Document document) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
}