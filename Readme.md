# Информационная система "Железнодорожная станция"

![alt text](https://img.shields.io/badge/Java-17-orange.svg)



![alt text](https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen.svg)



![alt text](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)



![alt text](https://img.shields.io/badge/Security-Spring_Security_&_JWT-critical)



## 📄 Обзор проекта

Это комплексная информационная система, разработанная как RESTful API на основе **Spring Boot**. Проект эмулирует работу крупной железнодорожной пассажирской станции, управляя всеми ключевыми аспектами ее деятельности: от кадрового учета и обслуживания подвижного состава до управления маршрутами, продажи билетов и взаимодействия с пассажирами.

Система была разработана в рамках курсового проекта по Базам Данных ФИТ НГУ

## ✨ Ключевые возможности

Система предоставляет богатый функционал для различных групп пользователей (администраторы, авторизованные пользователи) и охватывает следующие домены:

- **Управление персоналом:**

    - Ведение учета сотрудников, их должностей, отделов и бригад.

    - Реализована сложная фильтрация и поиск сотрудников по множеству критериев с использованием **JPA Specifications**: отдел, бригада, стаж, возраст, пол, зарплата, наличие детей.

    - Учет прохождения медосмотров водителями локомотивов.

- **Управление подвижным составом:**

    - Каталог поездов, вагонов и посадочных мест с их характеристиками.

    - Отслеживание технического обслуживания (планового, рейсового) и ремонтов.

    - Фильтрация поездов по возрасту, количеству рейсов, датам ТО и ремонта.

- **Маршруты и расписание:**

    - Создание и управление маршрутами (внутренними, международными) с указанием станций и промежуточных остановок.

    - Составление и ведение расписания рейсов с привязкой к поездам, указанием времени, статуса и базовой цены.

    - Реализован поиск рейсов по станциям отправления и прибытия.

- **Обслуживание пассажиров:**

    - Транзакционная система продажи, бронирования и возврата билетов, обеспечивающая целостность данных (например, место становится недоступным при покупке).

    - Управление списком пассажиров, привязанных к аккаунту пользователя.

    - Учет багажа.

- **Безопасность и аутентификация:**

    - Надежная ролевая модель доступа (**ROLE_USER**, **ROLE_ADMIN**) на базе **Spring Security**.

    - Аутентификация на основе **JWT (JSON Web Tokens)**.

    - Защищенные эндпоинты в зависимости от роли пользователя.

- **Административная панель:**

    - Специальный эндпоинт для администраторов для выполнения произвольных SELECT, UPDATE, INSERT, DELETE SQL-запросов к базе данных с безопасной обработкой.


## 🛠️ Технологический стек

|   |   |
|---|---|
|Категория|Технология|
|**Бэкенд**|**Java 17**, **Spring Boot 3.4.5**, Spring Web, Spring Data JPA, Lombok|
|**База данных**|**PostgreSQL**, **Flyway** (для версионирования и миграций БД)|
|**Безопасность**|**Spring Security**, **JWT** (библиотека jjwt)|
|**Валидация**|Jakarta Bean Validation (hibernate-validator)|
|**Сборка**|**Gradle**|
|**Окружение**|**Docker**, **Docker Compose**|

## 🚀 Как запустить проект

### Предварительные требования

- **JDK 17** или выше.

- **Docker** и **Docker Compose**.

- Git.


### Пошаговая инструкция

1. **Клонируйте репозиторий:**

          `git clone <your-repository-url> cd RailWay-Backend`


2. **Настройте и запустите базу данных в Docker:**

    - Создайте в корне проекта файл docker-compose.yml со следующим содержимым:

              `version: '3.8'  services:   postgres-db:     image: 'postgres:12-alpine'     container_name: railway-db     environment:       POSTGRES_USER: postgres       POSTGRES_PASSWORD: 1234       POSTGRES_DB: railway     ports:       - "5432:5432"     volumes:       - postgres_data:/var/lib/postgresql/data     restart: always  volumes:   postgres_data:`


        IGNORE_WHEN_COPYING_START
        
        content_copy download
        
        Use code [with caution](https://support.google.com/legal/answer/13505487). Yaml
        
        IGNORE_WHEN_COPYING_END
        
    - Запустите контейнер с базой данных:
        
              `docker-compose up -d`
            
        
        IGNORE_WHEN_COPYING_START
        
        content_copy download
        
        Use code [with caution](https://support.google.com/legal/answer/13505487). Bash
        
        IGNORE_WHEN_COPYING_END

3. **Проверьте конфигурацию приложения:**

    - Файл src/main/resources/application.yml уже настроен для работы с базой данных из Docker-контейнера. Если вы меняли POSTGRES_USER или POSTGRES_PASSWORD в docker-compose.yml, не забудьте обновить их и в application.yml.

4. **Соберите и запустите приложение:**

    - При первом запуске **Flyway** автоматически выполнит все миграции, создаст таблицы и заполнит их начальными данными.

    - Выполните в терминале (в корне проекта):

              `# Для Linux/macOS ./gradlew bootRun  # Для Windows .\gradlew.bat bootRun`


        IGNORE_WHEN_COPYING_START
        
        content_copy download
        
        Use code [with caution](https://support.google.com/legal/answer/13505487). Bash
        
        IGNORE_WHEN_COPYING_END

5. **Готово!** Приложение будет доступно по адресу http://localhost:8080.