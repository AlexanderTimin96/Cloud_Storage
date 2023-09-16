package ru.netology.cloudStorage.service.file;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;
import ru.netology.cloudStorage.repository.FileRepository;
import ru.netology.cloudStorage.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, String fileName) {

    }

    @Override
    @Transactional
    public FileDTO downloadFile(String fileName) {
        return null;
    }

    @Override
    @Transactional
    public void editFileName(String fileName, FileDTO name) {

    }

    @Override
    @Transactional
    public void deleteFile(String fileName) {

    }

    @Override
    @Transactional
    public List<FileDTO> getAllFiles(int limit) {
        return null;
    }
}
