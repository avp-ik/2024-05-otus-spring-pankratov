package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ru.otus.hw.commons.TransferMultipartFile;
import ru.otus.hw.models.Document;
import ru.otus.hw.models.PrintedDocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// https://www.baeldung.com/thymeleaf-generate-pdf

@RequiredArgsConstructor
@Service
public class PrintedDocumentServiceImpl implements PrintedDocumentService {

    private final DocumentStoreService documentStoreService;

    @Override
    public ResponseEntity<byte[]> get(UUID id) {
        return documentStoreService.get(id);
    }

    @Override
    public Document create(PrintedDocument printedDocument) {

        var resultDocument = new Document();

        String html = parseThymeleafTemplate(printedDocument.getTemplate(), printedDocument.getData());

        byte[] pdf = generatePdfFromHtml(html);

        TransferMultipartFile transferMultipartFile = new TransferMultipartFile();
        transferMultipartFile.setInput(pdf);

        ResponseEntity<Document> responseEntity = documentStoreService.upload(transferMultipartFile);
        resultDocument = responseEntity.getBody();

        return resultDocument;
    }

    private String parseThymeleafTemplate(String templateName, HashMap<String, String> data) {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        for(Map.Entry<String, String> entry : data.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        return templateEngine.process(templateName, context);
    }

    public byte[] generatePdfFromHtml(String html) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(baos);

        return baos.toByteArray();
    }
}