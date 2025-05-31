package ru.otus.hw.services;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.configs.MinioConfig;
import ru.otus.hw.models.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final MinioConfig minioConfig;

    @Override
    public Document get(UUID uuid)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        Document document = new Document();

        try {
            document.setId(uuid);

            /* play.min.io for test and development. */
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioConfig.getEndpoint())
                            .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                            .build();

            // Get input stream to have content of 'my-objectname' from 'my-bucketname'
            InputStream stream =
                    minioClient.getObject(
                            GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(uuid.toString()).build());

            // Read the input stream and print to the console till EOF.
//            byte[] buf = new byte[16384];
//            int bytesRead;
//            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
//                System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));
//            }

            // Close the input stream.

            document.setBody(stream.readAllBytes());
            document.setName(uuid.toString());

            stream.close();
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
        return document;
    }

    @Override
    public Document put(Document document)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException{

        document.setId(UUID.randomUUID());

        try {
            /* play.min.io for test and development. */
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioConfig.getEndpoint())
                            .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                            .build();

            // Make bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
            if (!found) {
                // Make a new bucket.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioConfig.getBucketName()).build());
            } else {
                System.out.println("Bucket " + minioConfig.getBucketName() + " already exists.");
            }

            // Create some content for the object.

            {
                // Create a InputStream for object upload.
                ByteArrayInputStream bais = new ByteArrayInputStream(document.getBody());

                // Create object 'my-objectname' in 'my-bucketname' with content from the input stream.
                minioClient.putObject(
                        PutObjectArgs.builder().bucket(minioConfig.getBucketName()).object(document.getId().toString()).stream(
                                        bais, bais.available(), -1)
                                .build());
                bais.close();
                System.out.println("Name="
                        + document.getName()
                        + ", id=" + document.getId().toString()
                        + " is uploaded successfully");
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
        return document;
    }
}