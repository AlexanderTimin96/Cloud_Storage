package ru.netology.cloudStorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private String message;
    private long id;
}
