package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataForDocument {

    private String name;

    private String template;

    private HashMap<String, String> data;

    private Document document;
}