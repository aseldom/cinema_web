# job4j_cinema_web

## О проекте

Данное web-приложение представляет собой сайт по покупке билетов в кинотеатр.

Данное web-приложение реализует:
- регистрацию/вход пользователя
- вывод киносеансов и фильмов
- покупку билетов

## Стек технологий
* Spring Boot 2.4.8
* Thymeleaf 2.7.6
* Bootstrap 4.4.1
* Liquibase 4.15.0
* Sql2o 1.6.0
* PostgreSQL 14
* PostgreSQL JDBC Driver 42.5.1

## Окружение 
Java 17, Maven 3.8, PostgreSQL 14;

## Запуск проекта
- Импортировать проект в IntelliJ IDEA
- В PostgreSQL создать БД cinema
- В Maven выполнить команду Plugins\liquibase\liquibase:update
- Выполнить метод main
- Открыть веб-браузер по адресу: 127.0.0.1:8080

## Screenshot
- Главная страница
![](/img/main_page.png)