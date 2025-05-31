package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.hw.models.Document;
import ru.otus.hw.services.DocumentService;

import javax.print.Doc;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// https://habr.com/ru/articles/675716/

@Slf4j
@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/api/v1/docs/{id}")
    public ResponseEntity<byte[]> get(@PathVariable("id") UUID id) {
        try {
//            URL res = getClass().getClassLoader().getResource("sample.pdf");
//            File file = Paths.get(res.toURI()).toFile();
//            String absolutePath = file.getAbsolutePath();

//            Path path = Paths.get("sample.pdf");
//            byte[] pdfBytes = Files.readAllBytes(Paths.get(res.toURI()));

            Document document = documentService.get(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", document.getName()); // or "attachment"

            return new ResponseEntity<byte[]>(document.getBody(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PostMapping(value = "/api/v1/docs", produces = "application/pdf")
    @PostMapping(value = "/api/v1/docs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> upload(@RequestPart("file") MultipartFile file) {

        Document document = new Document();

        try {

            if (file.isEmpty()) {
                document.setMessage("Please upload a file!");
                return new ResponseEntity<Document>(document, HttpStatus.BAD_REQUEST);
            }

            document.setBody(file.getBytes());

            document = documentService.put(document);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //headers.setContentDispositionFormData("inline", document.getName()); // or "attachment"

            document.setMessage("File uploaded successfully!");
            return new ResponseEntity<Document>(document, headers, HttpStatus.OK);

        } catch (Exception e) {
            document.setMessage("Failed to upload file!");
            return new ResponseEntity<Document>(document, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}