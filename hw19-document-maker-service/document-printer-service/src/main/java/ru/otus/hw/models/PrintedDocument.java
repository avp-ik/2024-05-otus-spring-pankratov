package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintedDocument {

    private UUID id;

    private String name;

    private String template;

    private HashMap<String, String> data;

    private String url;

    private byte[] body;
}