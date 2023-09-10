package ru.netology.cloudStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true)
    private String hash;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false)
    private byte[] fileByte;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = true)
    private LocalDateTime updatedDate;

    private boolean isDelete = false;
}
