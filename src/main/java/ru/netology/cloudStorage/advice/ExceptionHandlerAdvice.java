package ru.netology.cloudStorage.advice;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.cloudStorage.exception.FileNotFoundException;
import ru.netology.cloudStorage.exception.InvalidInputDataException;
import ru.netology.cloudStorage.exception.UserAlreadyCreatedException;
import ru.netology.cloudStorage.exception.UserNotFoundException;
import ru.netology.cloudStorage.model.Error;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error fileNotFoundHandler(FileNotFoundException exc) {
        return new Error(exc.getMessage(), exc.getId());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userNotFoundHandler(UserNotFoundException exc) {
        return new Error(exc.getMessage(), exc.getId());
    }

    @ExceptionHandler(UserAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error userAlreadyCreatedHandler(UserAlreadyCreatedException exc) {
        return new Error(exc.getMessage(), exc.getId());
    }

    @ExceptionHandler(InvalidInputDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error uInvalidInputDataHandler(InvalidInputDataException exc) {
        return new Error(exc.getMessage(), exc.getId());
    }
}
