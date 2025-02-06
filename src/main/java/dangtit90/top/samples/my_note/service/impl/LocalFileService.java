package dangtit90.top.samples.my_note.service.impl;

import dangtit90.top.samples.my_note.config.AppProperties;
import dangtit90.top.samples.my_note.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@Profile("local")
public class LocalFileService implements FileService {
    @Autowired
    private AppProperties properties;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        File uploadsDir = new File(properties.getUploadDir());
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        String fileId = UUID.randomUUID() + "." +
                file.getOriginalFilename().split("\\.")[1];
        file.transferTo(new File(properties.getUploadDir() + fileId));
        return fileId;
    }

    @Override
    public InputStream downloadFile(String fileName) throws Exception {
        File initialFile = new File(properties.getUploadDir() + fileName);
        return new FileInputStream(initialFile);
    }
}
