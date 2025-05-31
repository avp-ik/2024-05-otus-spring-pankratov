package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.models.DataForDocument;
import ru.otus.hw.models.Document;
import ru.otus.hw.services.DocumentMakerService;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/docs")
public class DocumentMakerController {

    private final DocumentMakerService documentMakerService;

    @GetMapping()
    public DataForDocument getDataForDocument() {

        DataForDocument dataForDocument = new DataForDocument();
        dataForDocument.setName("Monthly Report");
        dataForDocument.setTemplate("templates/base_form.html");

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "Sasha");
        hashMap.put("2", "Masha");

        dataForDocument.setData(hashMap);

        Document document = new Document();
        dataForDocument.setDocument(document);

        return dataForDocument;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDocument(@PathVariable("id") UUID id) {

        return documentMakerService.getDocument(id);
    }

    @PostMapping()
    public Document createDocument(@RequestBody @Valid DataForDocument dataForDocument) {

        return documentMakerService.createDocument(dataForDocument);
        // {"name":"Monthly Report","template":"templates/base_form.html","data":{"1":"Sasha","2":"Masha"},"document":{"id":null,"name":null,"body":null,"message":null}}
    }
}