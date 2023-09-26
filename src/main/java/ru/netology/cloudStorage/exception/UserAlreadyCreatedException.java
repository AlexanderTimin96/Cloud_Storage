package ru.netology.cloudStorage.exception;

public class UserAlreadyCreatedException extends RuntimeException {

    private final long id;

    public UserAlreadyCreatedException(String msg, long id) {
        super(msg);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
