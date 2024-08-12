package com.ademozalp.yemek_burada.client.firebase;

import com.ademozalp.yemek_burada.exception.YemekBuradaException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class ImageService {

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    private String uploadFile(File file, String fileName) {
        BlobId blobId = BlobId.of("image-storage-8513d.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

        try (InputStream inputStream = ImageService.class.getClassLoader().getResourceAsStream(
                "image-storage-8513d-firebase-adminsdk-46n15-045d81f584.json")) {

            assert inputStream != null;
            Credentials credentials = GoogleCredentials.fromStream(inputStream);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
            storage.create(blobInfo, Files.readAllBytes(file.toPath()));

            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/image-storage-8513d.appspot.com/o/%s?alt=media";
            return String.format(downloadUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        } catch (IOException exception) {
            throw new YemekBuradaException("Fotoğraf yüklenirken hata oluştu");
        }
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String upload(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);
            String url = this.uploadFile(file, fileName);
            file.delete();
            return url;
        } catch (Exception exception) {
            log.error("Image yüklenirken hata oluştu: {}", exception.getMessage());
            throw new YemekBuradaException(exception.getMessage());
        }
    }

}