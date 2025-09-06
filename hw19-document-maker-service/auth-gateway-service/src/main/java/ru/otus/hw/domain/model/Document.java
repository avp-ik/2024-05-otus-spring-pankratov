package ru.otus.hw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    UUID id;

    String name;

    byte[] body;

    String message;
}