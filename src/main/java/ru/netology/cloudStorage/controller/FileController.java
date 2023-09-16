package ru.netology.cloudStorage.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudStorage.DTO.FileDTO;
import ru.netology.cloudStorage.service.file.FileService;

import java.util.List;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PreAuthorize("hasAuthority('ROLE_UPLOAD')")
    @PostMapping("/file")
    public ResponseEntity<Void> uploadFile(@NotNull @RequestPart("file") MultipartFile file,
                                           @RequestParam("filename") String fileName) {
        fileService.uploadFile(file, fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    @DeleteMapping("/file")
    public ResponseEntity<Void> deleteFile(@RequestParam String fileName) {
        fileService.deleteFile(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DOWNLOAD')")
    @GetMapping("/file")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@RequestParam String filename) {
        FileDTO file = fileService.downloadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getType()))
                .contentLength(file.getSize())
                .body(file.getFileByte());
    }

    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    @PutMapping("/file")
    public ResponseEntity<Void> EditFileName(@RequestParam String filename, @RequestBody FileDTO fileDTO) {
        fileService.editFileName(filename, fileDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileDTO>> getAllFiles(@Min(1) @RequestParam int limit) {
        return new ResponseEntity<>(fileService.getAllFiles(limit), HttpStatus.OK);
    }
}
