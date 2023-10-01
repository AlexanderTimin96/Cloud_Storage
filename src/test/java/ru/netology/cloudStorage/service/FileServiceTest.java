package ru.netology.cloudStorage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.netology.cloudStorage.DTO.FileDTO;
import ru.netology.cloudStorage.entity.file.File;
import ru.netology.cloudStorage.entity.user.User;
import ru.netology.cloudStorage.exception.FileNotFoundException;
import ru.netology.cloudStorage.repository.FileRepository;
import ru.netology.cloudStorage.security.JwtProvider;
import ru.netology.cloudStorage.service.file.FileService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FileServiceTest {

    private static final String MY_FILE_NAME = "fileName.txt";

    @Autowired
    private FileService fileService;
    @MockBean
    private FileRepository fileRepository;
    @MockBean
    private JwtProvider jwtProvider;

    private User user;
    private File file;

    @BeforeEach
    public void init() {
        user = User.builder().id(1L).build();
        file = File.builder()
                .Id(1L)
                .fileName(MY_FILE_NAME)
                .hash("0b28122bac2239a5968bdb1112cae820")
                .type(MediaType.TEXT_PLAIN_VALUE)
                .size(4L)
                .fileByte("test".getBytes())
                .createdDate(LocalDateTime.now())
                .user(user)
                .build();
    }

    @Test
    void testDownloadFile_thenSuccess() {

        Mockito.when(jwtProvider.getAuthorizedUser()).thenReturn(user);
        Mockito.when(fileRepository.findFileByUserIdAndFileName(1L, MY_FILE_NAME))
                .thenReturn(Optional.ofNullable(file));

        FileDTO downloadFile = fileService.downloadFile(MY_FILE_NAME);

        Assertions.assertEquals(MY_FILE_NAME, downloadFile.getFileName());
    }

    @Test
    void testEditFileName_thenSuccess() {
        FileDTO newName = new FileDTO();
        newName.setFileName("newName.txt");
        Mockito.when(jwtProvider.getAuthorizedUser()).thenReturn(user);
        Mockito.when(fileRepository.findFileByUserIdAndFileName(1L, MY_FILE_NAME))
                .thenReturn(Optional.ofNullable(file));

        fileService.editFileName(MY_FILE_NAME, newName);

        Mockito.verify(fileRepository,
                Mockito.times(1)).save(file);
    }

    @Test
    void testDeleteFile_thenSuccess() {
        Mockito.when(jwtProvider.getAuthorizedUser()).thenReturn(user);
        Mockito.when(fileRepository.findFileByUserIdAndFileName(1L, MY_FILE_NAME))
                .thenReturn(Optional.ofNullable(file));

        fileService.deleteFile(MY_FILE_NAME);

        Mockito.verify(fileRepository,
                Mockito.times(1)).save(file);
    }

    @Test
    void testGetAllFiles_thenSuccess() {
        int limit = 3;
        List<File> listFile = List.of(
                File.builder().size(1233L).fileName("file1.txt").build(),
                File.builder().size(32272L).fileName("file2.txt").build(),
                File.builder().size(2353L).fileName("file3.txt").build());
        Mockito.when(jwtProvider.getAuthorizedUser()).thenReturn(user);
        Mockito.when(fileRepository.findFilesByUserIdWithLimit(user.getId(), limit))
                .thenReturn(listFile);

        List<FileDTO> files = fileService.getAllFiles(limit);

        Assertions.assertEquals("file1.txt", files.get(0).getFileName());
        Assertions.assertEquals("file2.txt", files.get(1).getFileName());
        Assertions.assertEquals("file3.txt", files.get(2).getFileName());
    }

    @Test
    void testDownloadFileIsEmpty_thenFileNotFoundException() {
        Mockito.when(jwtProvider.getAuthorizedUser())
                .thenReturn(user);
        Mockito.when(fileRepository.findFileByUserIdAndFileName(1L, MY_FILE_NAME))
                .thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> fileService.downloadFile(MY_FILE_NAME));
    }
}
