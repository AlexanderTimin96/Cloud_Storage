package ru.netology.cloudStorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudStorage.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
