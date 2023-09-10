package ru.netology.cloudStorage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public void uploadFile(MultipartFile file, String fileName) {

    }

    @Override
    public FileDTO downloadFile(String fileName) {
        return null;
    }

    @Override
    public void editFileName(String fileName, FileDTO name) {
        
    }

    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public List<FileDTO> getAllFiles(int limit) {
        return null;
    }
}
