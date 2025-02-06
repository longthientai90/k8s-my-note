package dangtit90.top.samples.my_note.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    String uploadFile(MultipartFile file) throws Exception;

    InputStream downloadFile(String fileName) throws Exception;
}
