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

    UUID id;

    String name;

    String template;

    HashMap<String, String> data;

    String url;

    byte[] body;
}