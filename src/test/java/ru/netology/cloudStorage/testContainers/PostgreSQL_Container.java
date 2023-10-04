package ru.netology.cloudStorage.testContainers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQL_Container extends PostgreSQLContainer<PostgreSQL_Container> {

    public static final String IMAGE_VERSION = "postgres:alpine";
    public static final String DATABASE_NAME = "postgres";
    public static PostgreSQLContainer container;

    public PostgreSQL_Container() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance() {
        container = new PostgreSQL_Container().withDatabaseName(DATABASE_NAME);
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("jdbc:postgresql://postgres:5432/postgres", container.getJdbcUrl());
        System.setProperty("postgres", container.getUsername());
        System.setProperty("postgresql", container.getPassword());
    }

    @Override
    public void stop() {
    }
}