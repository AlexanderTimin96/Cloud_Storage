package ru.netology.cloudStorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;

import java.util.List;

public interface FileService {

    void uploadFile(MultipartFile file, String fileName);

    FileDTO downloadFile(String fileName);

    void editFileName(String fileName, FileDTO name);

    void deleteFile(String fileName);

    List<FileDTO> getAllFiles(int limit);
}
