package ru.otus.hw.commons;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

// https://www.baeldung.com/java-convert-byte-array-to-multipartfile

@Data
@RequiredArgsConstructor
public class TransferMultipartFile implements MultipartFile {

    private byte[] input;

    @Override
    public String getName() {
        return "file";
    }

    @Override
    public String getOriginalFilename() {
        return "file";
    }

    @Override
    public String getContentType() {
        return MediaType.MULTIPART_FORM_DATA_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public void transferTo(File destination) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(input);
        }
    }
}