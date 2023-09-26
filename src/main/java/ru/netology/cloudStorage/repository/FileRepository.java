package ru.netology.cloudStorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.netology.cloudStorage.entity.file.File;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findFileByUserIdAndFileName(Long userId, String filename);

    @Query(value = "select * from files s where s.user_id = ?1 order by s.id desc limit ?2", nativeQuery = true)
    List<File> findFilesByUserIdWithLimit(Long userId, int limit);
}
