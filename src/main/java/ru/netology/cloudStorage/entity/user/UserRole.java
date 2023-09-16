package ru.netology.cloudStorage.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {

    UPLOAD("UPLOAD"),
    UPDATE("UPDATE"),
    DOWNLOAD("DOWNLOAD"),
    DELETE("DELETE");
    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}