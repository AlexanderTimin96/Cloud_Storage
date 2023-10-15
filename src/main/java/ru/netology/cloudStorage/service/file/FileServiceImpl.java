package ru.netology.cloudStorage.service.file;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;
import ru.netology.cloudStorage.entity.file.File;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.exception.FileNotFoundException;
import ru.netology.cloudStorage.exception.InvalidInputDataException;
import ru.netology.cloudStorage.repository.FileRepository;
import ru.netology.cloudStorage.security.JwtProvider;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final JwtProvider jwtProvider;


    @Override
    public void uploadFile(@NonNull MultipartFile file, String fileName) {
        if (file.isEmpty()) {
            log.error("File not attached: {}", fileName);
            throw new FileNotFoundException("File not attached", 0);
        }

        Long userId = jwtProvider.getAuthorizedUser().getId();

        if (fileRepository.findFileByUserIdAndFileName(userId, fileName).isPresent()) {
            log.error("Downloading an existing file: {}", fileName);
            throw new InvalidInputDataException("This file already uploaded. Please upload other file", userId);
        }

        String hash = getHashOfFile(file);
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            log.error("Error reading file bytes: {}", fileName);
            throw new InvalidInputDataException("The file is not readable", userId);
        }

        fileRepository.save(File.builder()
                .hash(hash)
                .fileName(fileName)
                .type(file.getContentType())
                .size(file.getSize())
                .fileByte(fileBytes)
                .createdDate(LocalDateTime.now())
                .user(User.builder().id(userId).build())
                .build());

        log.info("Creating file and save to storage: {}", fileName);
    }


    @Override
    public FileDTO downloadFile(String fileName) {
        Long userId = jwtProvider.getAuthorizedUser().getId();

        File file = getFileFromStorage(fileName, userId);

        log.info("Download file: {}", fileName);
        return FileDTO.builder()
                .fileName(file.getFileName())
                .type(file.getType())
                .fileByte(file.getFileByte())
                .build();
    }

    @Override
    public void editFileName(String fileName, FileDTO fileDTO) {
        Long userId = jwtProvider.getAuthorizedUser().getId();

        File file = getFileFromStorage(fileName, userId);
        file.setFileName(fileDTO.getFileName());

        log.info("Edit name file: {} to {}", fileName, fileDTO.getFileName());
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(String fileName) {
        Long userId = jwtProvider.getAuthorizedUser().getId();

        File file = getFileFromStorage(fileName, userId);
        file.setDelete(true);
        file.setUpdatedDate(LocalDateTime.now());

        log.info("Set flag isDelete on file from storage " +
                "by file name {} and userID {}", file.getFileName(), userId);
        fileRepository.save(file);
    }

    @Override
    public List<FileDTO> getAllFiles(int limit) {
        Long userId = jwtProvider.getAuthorizedUser().getId();

        List<File> filesByUserIdWithLimit = fileRepository.findFilesByUserIdWithLimit(userId, limit);
        return filesByUserIdWithLimit.stream().filter(file -> !file.isDelete())
                .map(file -> FileDTO.builder()
                        .fileName(file.getFileName())
                        .type(file.getType())
                        .date(file.getCreatedDate())
                        .size(file.getSize())
                        .build())
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private String getHashOfFile(MultipartFile file) {

        MessageDigest md = MessageDigest.getInstance("MD5");

        try (InputStream fis = file.getInputStream()) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
        }
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    private File getFileFromStorage(String fileName, Long userId) {
        if (fileRepository.findFileByUserIdAndFileName(userId, fileName).isEmpty()) {
            log.error("File in storage by file name {} and UserID {} not found", fileName, userId);
            throw new FileNotFoundException("File in storage by file name " + fileName
                    + " and UserID " + userId + " not found", userId);
        }
        return fileRepository.findFileByUserIdAndFileName(userId, fileName).get();
    }
}
