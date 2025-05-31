package ru.otus.hw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataForDocument {

    String name;

    String template;

    HashMap<String, String> data;

    Document document;

}