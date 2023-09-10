package ru.netology.cloudStorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudStorage.entity.UsersEntity;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {
}
