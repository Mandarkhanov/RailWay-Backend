-- Установка кодировки и стандартных строк
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

CREATE TABLE departments (
    department_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE positions (
    position_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    department_id INTEGER REFERENCES departments(department_id) ON DELETE RESTRICT, -- Запретить удаление отдела, если есть должности
    min_salary DECIMAL(10,2) CHECK (min_salary >= 0),
    max_salary DECIMAL(10,2) CHECK (max_salary >= min_salary AND max_salary >= 0),
    description TEXT
);

CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE CHECK (birth_date < CURRENT_DATE),
    gender CHAR(1) CHECK (gender IN ('М', 'Ж')),
    hire_date DATE NOT NULL CHECK (hire_date <= CURRENT_DATE),
    position_id INTEGER REFERENCES positions(position_id) ON DELETE RESTRICT, -- Запретить удаление должности, если есть сотрудники
    salary DECIMAL(10,2) CHECK (salary >= 0),
    children_count INTEGER DEFAULT 0 CHECK (children_count >= 0),
    is_active BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE medical_examinations (
    examination_id SERIAL PRIMARY KEY,
    employee_id INTEGER REFERENCES employees(employee_id) ON DELETE CASCADE, -- При удалении сотрудника удалить его осмотры
    examination_date DATE NOT NULL,
    result BOOLEAN NOT NULL, -- TRUE = годен, FALSE = не годен
    notes TEXT
);

CREATE TABLE brigades (
    brigade_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    department_id INTEGER REFERENCES departments(department_id) ON DELETE SET NULL, -- Если отдел удалят, бригада останется без отдела
    manager_id INTEGER REFERENCES employees(employee_id) ON DELETE SET NULL -- Если менеджер уволится/удален, обнулить ссылку
);

CREATE TABLE employee_brigade (
    employee_id INTEGER REFERENCES employees(employee_id) ON DELETE CASCADE, -- Если сотрудник удален, убрать его из бригад
    brigade_id INTEGER REFERENCES brigades(brigade_id) ON DELETE CASCADE, -- Если бригада расформирована, сотрудники убираются из этой связи
    assignment_date DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (employee_id, brigade_id)
);

CREATE TABLE trains (
    train_id SERIAL PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    build_date DATE,
    last_maintenance_date DATE,
    status VARCHAR(50) NOT NULL CHECK (status IN ('в порядке', 'требует ремонта', 'в ремонте', 'списан'))
);

CREATE TABLE train_types (
    type_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK (name IN ('скорый', 'пассажирский', 'грузовой'))
);

CREATE TABLE stations (
    station_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(100),
    region VARCHAR(100)
);

CREATE TABLE route_categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE CHECK (name IN ('внутренний', 'международный', 'туристический', 'специальный', 'пригородный'))
);

CREATE TABLE routes (
    route_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    start_station INTEGER NOT NULL REFERENCES stations(station_id) ON DELETE RESTRICT,
    end_station INTEGER NOT NULL REFERENCES stations(station_id) ON DELETE RESTRICT,
    distance_km INTEGER CHECK (distance_km > 0),
    category_id INTEGER REFERENCES route_categories(category_id) ON DELETE SET NULL,
    CHECK (start_station <> end_station)
);

CREATE TABLE route_stops (
    route_stop_id SERIAL PRIMARY KEY,
    route_id INTEGER NOT NULL REFERENCES routes(route_id) ON DELETE CASCADE,
    station_id INTEGER NOT NULL REFERENCES stations(station_id) ON DELETE RESTRICT,
    stop_order INTEGER NOT NULL CHECK (stop_order > 0), -- Порядковый номер остановки на маршруте (1, 2, 3...)
    arrival_offset INTERVAL, -- Смещение времени прибытия относительно отправления с начальной станции (может быть NULL для первой остановки)
    departure_offset INTERVAL, -- Смещение времени отправления относительно отправления с начальной станции (может быть NULL для конечной)
    platform VARCHAR(10), -- Номер пути/платформы
    UNIQUE (route_id, stop_order), -- Остановка должна быть уникальной по порядку в маршруте
    UNIQUE (route_id, station_id) -- Станция должна быть уникальной в маршруте (обычно)
);

CREATE TABLE schedules (
    schedule_id SERIAL PRIMARY KEY,
    train_id INTEGER REFERENCES trains(train_id) ON DELETE RESTRICT,
    train_number VARCHAR(10) NOT NULL,
    type_id INTEGER REFERENCES train_types(type_id) ON DELETE RESTRICT,
    train_status VARCHAR(50) NOT NULL CHECK (train_status IN ('по расписанию', 'задержан', 'отменен', 'выполнен', 'в пути')),
    route_id INTEGER REFERENCES routes(route_id) ON DELETE RESTRICT,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    base_price DECIMAL(10,2) NOT NULL CHECK (base_price >= 0),
    CHECK (arrival_time > departure_time)
);

CREATE TABLE train_crews (
    crew_assignment_id SERIAL PRIMARY KEY,
    schedule_id INTEGER NOT NULL REFERENCES schedules(schedule_id) ON DELETE CASCADE,
    brigade_id INTEGER NOT NULL REFERENCES brigades(brigade_id) ON DELETE RESTRICT,
    assignment_date DATE NOT NULL DEFAULT CURRENT_DATE,
    UNIQUE (schedule_id, brigade_id)
);

CREATE TABLE maintenance (
    maintenance_id SERIAL PRIMARY KEY,
    train_id INTEGER NOT NULL REFERENCES trains(train_id) ON DELETE CASCADE,
    brigade_id INTEGER REFERENCES brigades(brigade_id) ON DELETE SET NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP CHECK (end_date >= start_date),
    type VARCHAR(50) NOT NULL CHECK (type IN ('плановый', 'рейсовый', 'аварийный', 'деповской',
    'ТО-1', 'ТО-2', 'ТР-1', 'ТР-2', 'ТР-3', 'КР-1', 'КР-2')),-- Расширены типы ТО
    result TEXT,
    is_repair BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE passengers (
    passenger_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),
    birth_date DATE CHECK (birth_date < CURRENT_DATE),
    gender CHAR(1) CHECK (gender IN ('М', 'Ж')),
    passport_series VARCHAR(10) NOT NULL,
    passport_number VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE,
    UNIQUE (passport_series, passport_number)
);

CREATE TABLE luggage (
    luggage_id SERIAL PRIMARY KEY,
    weight_kg DECIMAL(5,2) CHECK (weight_kg >= 0),
    pieces INTEGER CHECK (pieces >= 0),
    status VARCHAR(50) CHECK (status IN ('зарегистрирован', 'в пути', 'выдан', 'потерян'))
);

CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    car_number VARCHAR(20) NOT NULL UNIQUE,
    train_id INTEGER REFERENCES trains(train_id) ON DELETE SET NULL,
    car_type VARCHAR(50) NOT NULL CHECK (car_type IN ('плацкарт', 'купе', 'спальный', 'сидячий', 'ресторан', 'багажный')),
    capacity INTEGER CHECK (capacity >= 0),
    build_date DATE,
    status VARCHAR(50) DEFAULT 'в эксплуатации' CHECK (status IN ('в эксплуатации', 'в ремонте', 'списан'))
);

CREATE TABLE seats (
    seat_id SERIAL PRIMARY KEY,
    car_id INTEGER NOT NULL REFERENCES cars(car_id) ON DELETE CASCADE,
    seat_number VARCHAR(10) NOT NULL,
    seat_type VARCHAR(50) CHECK (seat_type IN ('нижнее', 'верхнее', 'боковое нижнее', 'боковое верхнее', 'у окна', 'у прохода')),
    is_available BOOLEAN DEFAULT TRUE NOT NULL,
    features TEXT,
    UNIQUE (car_id, seat_number)
);

CREATE TABLE tickets (
    ticket_id SERIAL PRIMARY KEY,
    schedule_id INTEGER NOT NULL REFERENCES schedules(schedule_id) ON DELETE RESTRICT,
    passenger_id INTEGER NOT NULL REFERENCES passengers(passenger_id) ON DELETE RESTRICT,
--    car_id INTEGER NOT NULL REFERENCES cars(car_id) ON DELETE RESTRICT, ЛИШНЕЕ (В МЕСТЕ УЖЕ ЕСТЬ ВАГОН)
    seat_id INTEGER NOT NULL REFERENCES seats(seat_id) ON DELETE RESTRICT,
    luggage_id INTEGER REFERENCES luggage(luggage_id) ON DELETE SET NULL,
    purchase_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    ticket_status VARCHAR(50) NOT NULL DEFAULT 'забронирован' CHECK (ticket_status IN ('забронирован', 'оплачен', 'возвращен', 'использован')),
    UNIQUE (schedule_id, seat_id)
);