# Ads Service

Сервис управления пользователями и объявлениями предоставляет функционал для работы с профилями пользователей, загрузки изображений и управления контентом. Реализована аутентификация, обновление данных пользователей, а также обработка изображений для аватаров и объявлений.

## Использованные технологии

- **Java** ([oracle.com/java](https://www.oracle.com/java/))
- **Spring Boot** ([spring.io/projects/spring-boot](https://spring.io/projects/spring-boot))
- **Spring Data JPA** ([spring.io/projects/spring-data-jpa](https://spring.io/projects/spring-data-jpa))
- **PostgreSQL** ([postgresql.org](https://www.postgresql.org/))
- **Swagger** ([swagger.io](https://swagger.io/))
- **Lombok** ([projectlombok.org](https://projectlombok.org/))
- **Maven** ([maven.apache.org](https://maven.apache.org/))
- **H2** ([h2database.com](https://www.h2database.com/html/main.html))
- **Liquibase** ([liquibase.com](https://www.liquibase.com/))
- **JUnit** ([junit.org/junit5](https://junit.org/junit5/))
- **Mockito** ([site.mockito.org](https://site.mockito.org/))
- **Git** ([git-scm.com](https://git-scm.com/))
- **Docker** ([docker.com](https://www.docker.com/))

## Установка и запуск проекта

### Необходимые инструменты

- Java 17 ([oracle.com/java](https://www.oracle.com/java/))
- Maven ([maven.apache.org](https://maven.apache.org/))
- PostgreSQL ([postgresql.org](https://www.postgresql.org/))
- Docker ([docker.com](https://www.docker.com/))

### Установка зависимостей

Для установки зависимостей выполните команду:

```bash
mvn clean install
```

### Настройка и запуск приложения

#### Backend

1. **Установите PostgreSQL**  
   Скачайте и установите PostgreSQL с официального сайта: [PostgreSQL Downloads](https://www.postgresql.org/download/).

2. **Создайте базу данных и пользователя**  
   Выполните следующие команды в командной строке PostgreSQL (psql):

   ```bash
   CREATE DATABASE skypro_homework;
   CREATE USER skypro WITH PASSWORD 'password';
   GRANT ALL PRIVILEGES ON DATABASE skypro_homework TO skypro;
   ```

3. **Настройте `application.properties`**  
   Создайте или обновите файл `src/main/resources/application.properties`:

   ```properties
   spring.application.name=skypro-homework
   build.version=1.0.0

   spring.datasource.url=jdbc:postgresql://localhost:5432/skypro_homework
   spring.datasource.username=skypro
   spring.datasource.password=password
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.liquibase.default-schema=public

   spring.jpa.hibernate.ddl-auto=validate
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

   spring.liquibase.change-log=classpath:db/changelog-master.yml
   ```

   Для тестирования с H2 добавьте:

   ```properties
   spring.datasource.url=jdbc:h2:file:./src/main/resources/skypro
   spring.datasource.driver-class-name=org.h2.Driver
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   ```

4. **Запустите приложение**  
   Выполните команду:

   ```bash
   mvn spring-boot:run
   ```

   Backend будет доступен по адресу: `http://localhost:8080`

#### Frontend

1. **Установите Docker**  
   Для установки Docker на ваш компьютер установите Docker Desktop:  
   [Docker Desktop](https://www.docker.com/products/docker-desktop/).

2. **Запустите фронтенд**  
   Откройте командную строку (или терминал) и выполните следующую команду:

   ```bash
   docker run -p 3000:3000 --rm ghcr.io/dmitry-bizin/front-react-avito:v1.21
   ```

   После выполнения команды фронтенд запустится на порту `3000` и будет доступен по адресу: `http://localhost:3000`.

## Использование API

Для просмотра документации API используйте Swagger UI по адресу: `http://localhost:8080/swagger-ui/index.html`.

### Примеры запросов

#### Обновить пароль пользователя

```http
POST /users/set_password
Content-Type: application/json

{
  "currentPassword": "oldPass",
  "newPassword": "newPass"
}
```

#### Получить данные текущего пользователя

```http
GET /users/me
```

#### Обновить данные пользователя

```http
PATCH /users/me
Content-Type: application/json

{
  "firstName": "NewFirstName",
  "lastName": "NewLastName",
  "phone": "+79999999999"
}
```

#### Обновить изображение пользователя

```http
PATCH /users/me/image
Content-Type: multipart/form-data

form-data: image (file)
```

#### Получить изображение пользователя

```http
GET /users/me/image
```

#### Получить изображение объявления

```http
GET /ads/img/{adId}
```

#### Получить изображение по имени файла

```http
GET /images/{filename}
```

## Поддерживаемые функции

- **Аутентификация**: Используется Spring Security для проверки пользователя через `Authentication`.
- **Обработка изображений**: Поддержка PNG, JPEG, GIF. Изображения сохраняются на диске и в БД с генерацией превью.
- **Управление пользователями**: Обновление пароля, профиля, загрузка аватаров.
- **Управление объявлениями**: Получение изображений для объявлений.

## Тестирование

В проекте используются JUnit и Mockito для модульного тестирования. Покрыты сервисы (`UserServiceImpl`), контроллеры (`UserController`, `ImageController`) и обработка изображений (`ImageServiceImpl`).

Для запуска тестов выполните команду:

```bash
mvn test
```

## Команда проекта

Ситникова Анастасия