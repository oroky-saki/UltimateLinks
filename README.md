# Ultimate Links - REST сервис для создания коротких ссылок

## Содержание
- [Цель и описание проекта](#цель-создания-и-краткое-описание-проекта)
- [Технологии](#технологии)
- [Тестирование](#локальный-запуск-для-тестирования)

## Цель создания и краткое описание проекта
REST сервис для создания коротких ссылок создан для получения практики разработки и является учебным проектом.
Разработанный сервис коротких ссылок является аналогом таких сервисов как Bitly, Goo и др.

## Технологии
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL](https://www.postgresql.org/)
- [Lobmok](https://projectlombok.org/)
- [Java Beans Validation](https://www.baeldung.com/java-validation)
- [Mapstruct](https://mapstruct.org/)
- [Spring Retry](https://www.baeldung.com/spring-retry)
- [Swagger](https://swagger.io/specification/)
- [Flyway](https://flywaydb.org/)

## Локальный запуск для тестирования
Для запуска приложения необходима версия Java 20
1. Скачайте репозиторий на свой компьютер;
2. Откройте скачанный проект в редакторе кода IntelliJ IDEA/ VS Code/ etc.
3. Создайте базу данных в PostgreSQL с названием "links_ultimate" (схему создавать не нужно, используется стандартная public)
4. Пропишите в файле application.properties значения полей username и password для вашего пользователя в следующих строках:
      - spring.datasource.username
      - spring.datasource.password
      - spring.flyway.user
      - spring.flyway.password
5. Откройте Swagger-документацию по ссылке: http://localhost:8080/swagger-ui/index.html#/
6. Проестируйте REST API с помощью Postman или Swagger
      - РЕКОМЕНДУЮ тестировать через Swagger, так как не нужно регистрироваться и прописывать заголовки авторизации
      - Тестирование через Postman:
      - При тестировании через Postman необходимо во вкладке 'Authorization' ввести логин (почту) и пароль для авторизации в сервисе
      - Чтобы зарегестрироваться в сервисе необходимо по ссылке http://localhost:8080/registration отправить JSON объект из полей email, password, name (все они типа String) и в ответ получить 201 статус код. Возвращенный объект можете проигнорировать
      - Зарегестрированная в подпункте выше почта и пароль будут использоваться для дальнейшей авторизации в сервисе

