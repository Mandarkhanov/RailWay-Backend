CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ROLE_USER', 'ROLE_ADMIN'))
);

-- Пароль для 'user' = 'password'
-- Пароль для 'admin' = 'admin'
INSERT INTO users (name, email, password, role) VALUES
('Test User', 'user@example.com', '$2a$10$MXH1Y7UBVVMSeRa5KHhCuuMO.rKYR3WEl3QY0p74mNXkx8Jrv9eyy', 'ROLE_USER'),
('Admin User', 'admin@example.com', '$2a$10$l7gfi6YB/xRuLbv7PCy7M.2ugAh8N4FIE3RhbcZE.X4n7OYyPdAw.', 'ROLE_ADMIN');