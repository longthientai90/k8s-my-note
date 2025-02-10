package dangtit90.top.samples.my_note.service.impl;

import dangtit90.top.samples.my_note.config.MinioProperties;
import dangtit90.top.samples.my_note.service.FileService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class MinioFileService implements FileService {
    @Autowired
    MinioClient minioClient;
    @Autowired
    MinioProperties properties;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileId = UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[1];
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(properties.getBucket()).contentType(file.getContentType())
                .object(fileId)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build();
        minioClient.putObject(args);
        return fileId;
    }

    @Override
    public InputStream downloadFile(String fileName) throws Exception {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(properties.getBucket())
                .object(fileName)
                .build();
        return minioClient.getObject(args);
    }
}
