# Облачное хранилище (в процессе разработки)

## Задача

Данный проект является дипломной работой. С заданием можно
ознакомиться [здесь](https://github.com/netology-code/jd-homeworks/blob/master/diploma/cloudservice.md).

## Описание проекта

REST-сервис предоставляет интерфейс для интеграции с FRONT для облачного хранения данных.

Сервис предоставляет возможности:
- авторизации;


- регистрации новых пользователей;
- просмотра зарегистрированных пользователей (только ADMIN);
- удаления пользователей (только ADMIN);


- вывода списка файлов;
- добавления файлов в хранилище;
- удаление файлов из хранилища, с возможностью восстановления;
- восстановление файлов  (только ADMIN);

Скриншоты работы сервера:

<img src="pictureForReadme/login.PNG" alt="drawing" width="700"/>

<img src="pictureForReadme/work.PNG" alt="drawing" width="700"/>

## Запуск приложения

Для запуска введите в терминале:

```
./mvn clean package -Dskiptests
docker-compose up -d --build
```
Docker-образы запустятся на портах:
-FRONT: localhost:8080
-BACKEND: localhost:8081
-POSTGRESQL: localhost:5432


## Описание технической части проекта

- Приложение разработано с использованием Spring Boot;
- Использован сборщик пакетов Maven;
- Логирование с помощью Slf4j;
- При авторизации используется JWT Token и ролей пользователей (admin/user);
- База данных PostgreSQL;
- Миграции баз данных с помощью Liquibase;
- Для запуска используется docker, docker-compose;

## Описание архитектуры приложения

Архитектура приложения - многослойная (клиент-серверная), где процессы представления,
обработки и управления данными являются логически отделенными друг от друга процессами.

Модель многослойной архитектуры помогает создать гибкое и многократно используемое программное обеспечение.

## Эндпоинты

Авторизация:

* POST: `/login` - принимает объект user(login и password) возвращает auth-token, который используется в header
для идентификации пользователя на BACKEND для дальнейших запросов.
* POST: `/logout` - принимает объект auth-token в header и деактивирует его. 
Последующие запросы с этим токеном будут не авторизованы и вернут код 401. Необходимо быть авторизованным.

Управление пользователями:

* POST: `/create` - принимает объект user(login и password) и создает нового пользователя с ролью USER.
Возвращает данного пользователя.
* GET: `/{login}` - принимает login и возвращает пользователя по данному логину, если такой есть в БД (только ADMIN).
Необходимо быть авторизованным.
* DELETE: `/delete/{login}` - принимает login и удаляет его БД (только ADMIN). Необходимо быть авторизованным.

Управление файлами:

* POST: `/file` - принимает файл (multipart/form-data) и сохраняет его в БД. Необходимо быть авторизованным.
* DELETE: `/file` - принимает имя файла и устанавливает флаг isDelete в базе данных. 
Файл больше не виден пользователю.
* GET: `/file` - принимает имя файла и отправляет файл пользователю на загрузку. Необходимо быть авторизованным.
* PUT: `/file` - принимает имя файла которое следует заменить и новое имя файлу.
Производить переименование файла. Файл больше не виден пользователю. Необходимо быть авторизованным.
* GET: `/list` - принимает лимит файлов для отображения.
Возвращает файлы для отображения принадлежащие данному пользователю. Необходимо быть авторизованным.
* GET: `/list_deleted_files` - принимает лимит файлов для отображения и логин пользователя (только ADMIN). 
* Возвращает удаленные пользователем файлы. Необходимо быть авторизованным.
* PUT: `/restore_file` - принимает id файла и меняет флаг isDelete на false. 
Происходит восстановления файла для пользователя(только ADMIN).  Необходимо быть авторизованным.

## Тестирование

Для мануального тестирования доступны пользователи:
- User: 
  - login: user@test.ru
  - password: user
- Admin:
  - login: admin@test.ru
  - password: admin