package ru.netology.cloudStorage.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;
import ru.netology.cloudStorage.service.FileService;

import java.util.List;

@RestController

public class FileController {

    private final FileService fileService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping("/file")
    public ResponseEntity<Void> uploadFile(@NotNull @RequestPart("file") MultipartFile file,
                                           @RequestParam("filename") String fileName) {
        if (fileService.uploadFile(file, fileName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return null;
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<Void> deleteFile(@RequestParam String fileName) {
        if (fileService.deleteFile(fileName)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return null;
        }
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filename) {
        fileService.downloadFile(filename);
    }

    @PutMapping("/file")
    public ResponseEntity<Void> EditFileName(@RequestParam String filename, @RequestBody FileDTO fileDTO) {
        fileService.editFileName(filename, fileDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDTO>> getAllFiles(@Min(1) @RequestParam int limit) {
        return new ResponseEntity<>(fileService.getAllFiles(limit), HttpStatus.OK);
    }
}
