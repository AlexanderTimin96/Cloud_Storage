package ru.netology.cloudStorage.entity.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import ru.netology.cloudStorage.entity.file.File;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<File> cloudFileEntityList;
}
