package ru.netology.cloudStorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.cloudStorage.entity.file.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
