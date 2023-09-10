package ru.netology.cloudStorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;

import java.util.List;

public interface FileService {

    boolean uploadFile(MultipartFile file, String fileName);

    FileDTO downloadFile(String fileName);

    boolean editFileName(String fileName, FileDTO name);

    boolean deleteFile(String fileName);

    List<FileDTO> getAllFiles(int limit);
}
