INSERT INTO departments (name, description) VALUES
('Отдел эксплуатации', 'Управление движением поездов и подготовка составов'),
('Отдел ремонта и обслуживания', 'Ремонт и техническое обслуживание подвижного состава и инфраструктуры'),
('Отдел обслуживания пассажиров', 'Обслуживание пассажиров на вокзале и продажа билетов'),
('Отдел кадров', 'Управление персоналом'),
('Административный отдел', 'Общее управление и вспомогательные службы');


INSERT INTO positions (name, department_id, min_salary, max_salary, description) VALUES
-- Отдел эксплуатации
('Машинист локомотива', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), 70000.00, 110000.00, 'Управление локомотивом'),
('Помощник машиниста', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), 45000.00, 65000.00, 'Помощь машинисту'),
('Диспетчер движения поездов', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), 60000.00, 90000.00, 'Управление движением поездов по участку'),
('Осмотрщик вагонов', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), 40000.00, 55000.00, 'Осмотр технического состояния вагонов перед рейсом'),
('Составитель поездов', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), 45000.00, 60000.00, 'Формирование и расформирование составов'),
-- Отдел ремонта и обслуживания
('Техник по ремонту локомотивов', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), 50000.00, 75000.00, 'Ремонт и обслуживание локомотивов в депо'),
('Техник по ремонту вагонов', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), 48000.00, 70000.00, 'Ремонт и обслуживание вагонов в депо'),
('Монтер пути', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), 40000.00, 60000.00, 'Ремонт и обслуживание железнодорожных путей'),
('Начальник ремонтной бригады', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), 65000.00, 95000.00, 'Руководство ремонтной бригадой'),
-- Отдел обслуживания пассажиров
('Кассир билетный', (SELECT department_id FROM departments WHERE name = 'Отдел обслуживания пассажиров'), 35000.00, 50000.00, 'Продажа и оформление билетов'),
('Агент справочной службы', (SELECT department_id FROM departments WHERE name = 'Отдел обслуживания пассажиров'), 32000.00, 45000.00, 'Предоставление информации пассажирам'),
-- Отдел кадров
('Специалист по кадрам', (SELECT department_id FROM departments WHERE name = 'Отдел кадров'), 40000.00, 60000.00, 'Ведение кадрового учета и делопроизводства'),
-- Административный отдел
('Бухгалтер', (SELECT department_id FROM departments WHERE name = 'Административный отдел'), 50000.00, 80000.00, 'Ведение бухгалтерского учета'),
('Специалист по закупкам', (SELECT department_id FROM departments WHERE name = 'Административный отдел'), 45000.00, 70000.00, 'Обеспечение закупок для нужд ЖД');


INSERT INTO employees (first_name, last_name, birth_date, gender, hire_date, position_id, salary, children_count, is_active) VALUES
('Иван', 'Иванов', '1980-05-15', 'М', '2010-01-20', (SELECT position_id FROM positions WHERE name = 'Машинист локомотива'), 95000.00, 2, TRUE),
('Петр', 'Петров', '1985-11-22', 'М', '2012-03-10', (SELECT position_id FROM positions WHERE name = 'Помощник машиниста'), 60000.00, 1, TRUE),
('Сидор', 'Сидоров', '1978-07-30', 'М', '2008-06-01', (SELECT position_id FROM positions WHERE name = 'Машинист локомотива'), 105000.00, 0, TRUE),
('Анна', 'Диспетчерова', '1990-04-18', 'Ж', '2016-11-11', (SELECT position_id FROM positions WHERE name = 'Диспетчер движения поездов'), 75000.00, 0, TRUE),
('Сергей', 'Осмотров', '1987-09-05', 'М', '2014-08-08', (SELECT position_id FROM positions WHERE name = 'Осмотрщик вагонов'), 50000.00, 1, TRUE),
('Олег', 'Составителев', '1992-12-01', 'М', '2020-07-15', (SELECT position_id FROM positions WHERE name = 'Составитель поездов'), 55000.00, 0, TRUE),
('Николай', 'Локомотивин', '1983-06-18', 'М', '2011-05-03', (SELECT position_id FROM positions WHERE name = 'Машинист локомотива'), 80000.00, 1, TRUE), -- (Не годен - для примера)
('Дмитрий', 'Ремонтников', '1975-03-10', 'М', '2005-09-01', (SELECT position_id FROM positions WHERE name = 'Начальник ремонтной бригады'), 90000.00, 3, TRUE),
('Елена', 'Техникова', '1988-09-05', 'Ж', '2018-04-11', (SELECT position_id FROM positions WHERE name = 'Техник по ремонту локомотивов'), 65000.00, 0, TRUE),
('Мария', 'Вагонова', '1991-01-25', 'Ж', '2017-05-03', (SELECT position_id FROM positions WHERE name = 'Техник по ремонту вагонов'), 62000.00, 0, TRUE),
('Игорь', 'Путейцев', '1989-11-29', 'М', '2019-03-21', (SELECT position_id FROM positions WHERE name = 'Монтер пути'), 55000.00, 0, TRUE),
('Ольга', 'Кассирова', '1995-02-28', 'Ж', '2021-11-20', (SELECT position_id FROM positions WHERE name = 'Кассир билетный'), 45000.00, 0, TRUE),
('Алексей', 'Справочников', '1998-08-12', 'М', '2022-09-15', (SELECT position_id FROM positions WHERE name = 'Агент справочной службы'), 40000.00, 0, TRUE),
('Светлана', 'Кадровикова', '1982-06-07', 'Ж', '2013-02-14', (SELECT position_id FROM positions WHERE name = 'Специалист по кадрам'), 55000.00, 2, TRUE),
('Андрей', 'Бухгалтеров', '1970-10-04', 'М', '2000-12-01', (SELECT position_id FROM positions WHERE name = 'Бухгалтер'), 75000.00, 1, TRUE);


INSERT INTO medical_examinations (employee_id, examination_date, result, notes) VALUES
((SELECT employee_id FROM employees WHERE first_name = 'Иван' AND last_name = 'Иванов'), '2023-08-10', TRUE, 'Годен без ограничений'),                  -- Машинист Иванов (свежий осмотр)
((SELECT employee_id FROM employees WHERE first_name = 'Сидор' AND last_name = 'Сидоров'), '2023-09-01', TRUE, 'Годен'),                                -- Машинист Сидоров (свежий осмотр)
((SELECT employee_id FROM employees WHERE first_name = 'Николай' AND last_name = 'Локомотивин'), '2023-10-15', FALSE, 'Временно не годен по давлению'), -- Машинист Локомотивин (не прошел осмотр)
((SELECT employee_id FROM employees WHERE first_name = 'Петр' AND last_name = 'Петров'), '2023-07-25', TRUE, 'Годен');                                  -- Помощник машиниста Петров


INSERT INTO brigades (name, department_id, manager_id) VALUES
('Локомотивная бригада А', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), (SELECT employee_id FROM employees WHERE first_name = 'Иван' AND last_name = 'Иванов')), -- Машинист Иванов как менеджер
('Локомотивная бригада Б', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), (SELECT employee_id FROM employees WHERE first_name = 'Сидор' AND last_name = 'Сидоров')), -- Машинист Сидоров как менеджер
('Бригада по ремонту локомотивов', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), (SELECT employee_id FROM employees WHERE first_name = 'Дмитрий' AND last_name = 'Ремонтников')), -- Начальник ремонтной бригады Ремонтников как менеджер
('Бригада по ремонту вагонов', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), (SELECT employee_id FROM employees WHERE first_name = 'Дмитрий' AND last_name = 'Ремонтников')), -- Начальник ремонтной бригады Ремонтников как менеджер
('Бригада по ремонту пути', (SELECT department_id FROM departments WHERE name = 'Отдел ремонта и обслуживания'), (SELECT employee_id FROM employees WHERE first_name = 'Дмитрий' AND last_name = 'Ремонтников')),    -- Начальник ремонтной бригады Ремонтников как менеджер
('Бригада подготовки составов', (SELECT department_id FROM departments WHERE name = 'Отдел эксплуатации'), (SELECT employee_id FROM employees WHERE first_name = 'Олег' AND last_name = 'Составителев')); -- Составитель поездов Составителев как менеджер


INSERT INTO employee_brigade (employee_id, brigade_id, assignment_date) VALUES
((SELECT employee_id FROM employees WHERE first_name = 'Иван' AND last_name = 'Иванов'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада А'), '2022-01-01'), -- Машинист Иванов в Лок. бригаде А
((SELECT employee_id FROM employees WHERE first_name = 'Петр' AND last_name = 'Петров'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада А'), '2022-01-01'), -- Помощник Петров в Лок. бригаде А
((SELECT employee_id FROM employees WHERE first_name = 'Сидор' AND last_name = 'Сидоров'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада Б'), '2023-01-01'), -- Машинист Сидоров в Лок. бригаде Б
((SELECT employee_id FROM employees WHERE first_name = 'Николай' AND last_name = 'Локомотивин'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада Б'), '2023-03-15'), -- Машинист Локомотивин в Лок. бригаде Б
((SELECT employee_id FROM employees WHERE first_name = 'Елена' AND last_name = 'Техникова'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту локомотивов'), '2022-02-01'), -- Техник Техникова в бригаде ремонта лок.
((SELECT employee_id FROM employees WHERE first_name = 'Мария' AND last_name = 'Вагонова'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту вагонов'), '2023-03-15'), -- Техник Вагонова в бригаде ремонта вагонов
((SELECT employee_id FROM employees WHERE first_name = 'Игорь' AND last_name = 'Путейцев'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту пути'), '2023-04-01'), -- Монтер Путейцев в бригаде ремонта пути
((SELECT employee_id FROM employees WHERE first_name = 'Сергей' AND last_name = 'Осмотров'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада подготовки составов'), '2022-05-01'), -- Осмотрщик Осмотров в бригаде подготовки
((SELECT employee_id FROM employees WHERE first_name = 'Олег' AND last_name = 'Составителев'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада подготовки составов'), '2022-05-01'); -- Составитель Составителев в бригаде подготовки


INSERT INTO train_types (name) VALUES
('пассажирский'),
('скорый'),
('грузовой');


INSERT INTO stations (name, address, region) VALUES
('Москва Павелецкая', 'Павелецкая площадь, 1А', 'Москва'), -- station_id = 1
('Санкт-Петербург Московский', 'Невский проспект, 85', 'Санкт-Петербург'), -- station_id = 2
('Нижний Новгород Московский', 'Площадь Революции, 2', 'Нижегородская область'), -- station_id = 3
('Казань Пассажирская', 'Привокзальная площадь, 1', 'Республика Татарстан'), -- station_id = 4
('Сочи', 'улица Горького, 56А', 'Краснодарский край'), -- station_id = 5
('Владимир', 'Вокзальная площадь, 2', 'Владимирская область'); -- station_id = 6


INSERT INTO route_categories (name) VALUES
('внутренний'),
('международный'),
('туристический'),
('специальный'),
('пригородный');


INSERT INTO trains (model, build_date, last_maintenance_date, status) VALUES
('ЭП20', '2012-05-10', '2023-10-05', 'в порядке'),      -- (Скоростной локомотив)
('ЧС7', '1985-01-20', '2023-09-15', 'в порядке'),       -- (Пассажирский локомотив)
('2ТЭ116У', '2010-08-01', '2023-09-20', 'в порядке'),   -- (Грузовой локомотив)
('Ласточка', '2014-03-18', '2023-10-10', 'в ремонте'),  -- (Моторвагонный поезд (сидячий))
('ЭР2', '1975-01-01', '2023-11-01', 'требует ремонта'); -- (Пригородный поезд)


INSERT INTO routes (name, start_station, end_station, distance_km, category_id) VALUES
('Москва - Санкт-Петербург', (SELECT station_id FROM stations WHERE name = 'Москва Павелецкая'), (SELECT station_id FROM stations WHERE name = 'Санкт-Петербург Московский'), 700, (SELECT category_id FROM route_categories WHERE name = 'внутренний')),
('Москва - Нижний Новгород', (SELECT station_id FROM stations WHERE name = 'Москва Павелецкая'), (SELECT station_id FROM stations WHERE name = 'Нижний Новгород Московский'), 400, (SELECT category_id FROM route_categories WHERE name = 'внутренний')),
('Санкт-Петербург - Сочи', (SELECT station_id FROM stations WHERE name = 'Санкт-Петербург Московский'), (SELECT station_id FROM stations WHERE name = 'Сочи'), 2500, (SELECT category_id FROM route_categories WHERE name = 'внутренний')),
('Москва - Владимир (Пригородный)', (SELECT station_id FROM stations WHERE name = 'Москва Павелецкая'), (SELECT station_id FROM stations WHERE name = 'Владимир'), 180, (SELECT category_id FROM route_categories WHERE name = 'пригородный'));


INSERT INTO route_stops (route_id, station_id, stop_order, arrival_offset, departure_offset, platform) VALUES
-- Остановки для маршрута 'Москва - Санкт-Петербург' (route_id=1)
((SELECT route_id FROM routes WHERE name = 'Москва - Санкт-Петербург'), (SELECT station_id FROM stations WHERE name = 'Москва Павелецкая'), 1, NULL, '0 minutes'::INTERVAL, '1'), -- Москва (начальная)
((SELECT route_id FROM routes WHERE name = 'Москва - Санкт-Петербург'), (SELECT station_id FROM stations WHERE name = 'Владимир'), 2, '1 hours 45 minutes'::INTERVAL, '1 hours 50 minutes'::INTERVAL, '3'), -- Владимир (промежуточная)
((SELECT route_id FROM routes WHERE name = 'Москва - Санкт-Петербург'), (SELECT station_id FROM stations WHERE name = 'Санкт-Петербург Московский'), 3, '8 hours 0 minutes'::INTERVAL, NULL, '5'), -- Санкт-Петербург (конечная)
-- Остановки для маршрута 'Москва - Нижний Новгород' (route_id=2)
((SELECT route_id FROM routes WHERE name = 'Москва - Нижний Новгород'), (SELECT station_id FROM stations WHERE name = 'Москва Павелецкая'), 1, NULL, '0 minutes'::INTERVAL, '2'), -- Москва (начальная)
((SELECT route_id FROM routes WHERE name = 'Москва - Нижний Новгород'), (SELECT station_id FROM stations WHERE name = 'Владимир'), 2, '3 hours 30 minutes'::INTERVAL, '3 hours 35 minutes'::INTERVAL, '4'), -- Владимир (промежуточная)
((SELECT route_id FROM routes WHERE name = 'Москва - Нижний Новгород'), (SELECT station_id FROM stations WHERE name = 'Нижний Новгород Московский'), 3, '4 hours 0 minutes'::INTERVAL, NULL, '3'), -- Нижний Новгород (конечная)
-- Остановки для маршрута 'Санкт-Петербург - Сочи' (route_id=3)
((SELECT route_id FROM routes WHERE name = 'Санкт-Петербург - Сочи'), (SELECT station_id FROM stations WHERE name = 'Санкт-Петербург Московский'), 1, NULL, '0 minutes'::INTERVAL, '7'), -- Санкт-Петербург (начальная)
((SELECT route_id FROM routes WHERE name = 'Санкт-Петербург - Сочи'), (SELECT station_id FROM stations WHERE name = 'Казань Пассажирская'), 2, '20 hours 0 minutes'::INTERVAL, '20 hours 30 minutes'::INTERVAL, '2'), -- Казань (промежуточная)
((SELECT route_id FROM routes WHERE name = 'Санкт-Петербург - Сочи'), (SELECT station_id FROM stations WHERE name = 'Сочи'), 3, '40 hours 0 minutes'::INTERVAL, NULL, '1'), -- Сочи (конечная)
-- Остановки для маршрута 'Москва - Владимир (Пригородный)' (route_id=4)
((SELECT route_id FROM routes WHERE name = 'Москва - Владимир (Пригородный)'), (SELECT station_id FROM stations WHERE name = 'Москва Павелецкая'), 1, NULL, '0 minutes'::INTERVAL, '8'), -- Москва (начальная)
((SELECT route_id FROM routes WHERE name = 'Москва - Владимир (Пригородный)'), (SELECT station_id FROM stations WHERE name = 'Владимир'), 2, '1 hours 50 minutes'::INTERVAL, NULL, '1'); -- Владимир (конечная)


INSERT INTO schedules (train_id, train_number, type_id, train_status, route_id, departure_time, arrival_time, base_price) VALUES
((SELECT train_id FROM trains WHERE model = 'ЭП20'), '758А', (SELECT type_id FROM train_types WHERE name = 'скорый'), 'по расписанию', (SELECT route_id FROM routes WHERE name = 'Москва - Санкт-Петербург'), '2023-11-25 14:00:00', '2023-11-25 22:00:00', 3000.00),
((SELECT train_id FROM trains WHERE model = 'ЧС7'), '001М', (SELECT type_id FROM train_types WHERE name = 'пассажирский'), 'по расписанию', (SELECT route_id FROM routes WHERE name = 'Москва - Санкт-Петербург'), '2023-11-25 23:00:00', '2023-11-26 07:30:00', 2500.00),
((SELECT train_id FROM trains WHERE model = 'ЧС7'), '002Я', (SELECT type_id FROM train_types WHERE name = 'пассажирский'), 'по расписанию', (SELECT route_id FROM routes WHERE name = 'Москва - Нижний Новгород'), '2023-11-26 08:00:00', '2023-11-26 12:00:00', 1500.00),
((SELECT train_id FROM trains WHERE model = '2ТЭ116У'), '999Ч', (SELECT type_id FROM train_types WHERE name = 'грузовой'), 'в пути', (SELECT route_id FROM routes WHERE name = 'Санкт-Петербург - Сочи'), '2023-11-24 10:00:00', '2023-11-26 02:00:00', 5000.00),
((SELECT train_id FROM trains WHERE model = 'ЭР2'), '6616', (SELECT type_id FROM train_types WHERE name = 'пассажирский'), 'по расписанию', (SELECT route_id FROM routes WHERE name = 'Москва - Владимир (Пригородный)'), '2023-11-25 18:30:00', '2023-11-25 20:20:00', 400.00);


INSERT INTO train_crews (schedule_id, brigade_id, assignment_date) VALUES
((SELECT schedule_id FROM schedules WHERE train_number = '758А' AND departure_time = '2023-11-25 14:00:00'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада А'), '2023-11-25'), -- Лок. бригада А на рейсе 758А
((SELECT schedule_id FROM schedules WHERE train_number = '001М' AND departure_time = '2023-11-25 23:00:00'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада Б'), '2023-11-25'), -- Лок. бригада Б на рейсе 001М
((SELECT schedule_id FROM schedules WHERE train_number = '002Я' AND departure_time = '2023-11-26 08:00:00'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада А'), '2023-11-26'), -- Лок. бригада А на рейсе 002Я
-- Грузовой рейс 999Ч может иметь другую бригаду, но в тестовых данных пока пропустим для простоты
((SELECT schedule_id FROM schedules WHERE train_number = '6616' AND departure_time = '2023-11-25 18:30:00'), (SELECT brigade_id FROM brigades WHERE name = 'Локомотивная бригада Б'), '2023-11-25'); -- Лок. бригада Б на рейсе 6616


INSERT INTO maintenance (train_id, brigade_id, start_date, end_date, type, result, is_repair) VALUES
((SELECT train_id FROM trains WHERE model = 'ЭП20'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту локомотивов'), '2023-10-01 09:00:00', '2023-10-05 17:00:00', 'ТО-2', 'Плановое ТО-2 выполнено, локомотив готов', FALSE),
((SELECT train_id FROM trains WHERE model = 'Ласточка'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту вагонов'), '2023-10-10 08:00:00', NULL, 'ТР-1', 'Обнаружена серьезная неисправность ходовой части, ожидает запчастей', TRUE), -- Ласточка в ремонте
((SELECT train_id FROM trains WHERE model = 'ЧС7'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту локомотивов'), '2023-11-18 20:00:00', '2023-11-18 23:00:00', 'рейсовый', 'Устранено КЗ в цепи управления', TRUE),
((SELECT train_id FROM trains WHERE model = 'ЭР2'), (SELECT brigade_id FROM brigades WHERE name = 'Бригада по ремонту вагонов'), '2023-11-01 08:00:00', '2023-11-05 16:00:00', 'ТР-2', 'Проведен плановый ремонт с заменой части электрооборудования', TRUE); -- ЭР2 прошел ремонт


INSERT INTO passengers (first_name, last_name, middle_name, birth_date, gender, passport_series, passport_number, phone_number, email) VALUES
('Александр', 'Петров', 'Иванович', '1993-04-01', 'М', '1234', '567890', '89011234567', 'alex.petrov@example.com'),
('Екатерина', 'Иванова', 'Сергеевна', '1990-08-10', 'Ж', '5678', '123456', '89029876543', 'e.ivanova@example.com'),
('Николай', 'Смирнов', NULL, '2005-11-25', 'М', '9012', '345678', NULL, NULL), -- (Без отчества и контактов, может быть ребенок)
('Ольга', 'Кузнецова', 'Алексеевна', '1985-01-15', 'Ж', '3456', '789012', '89037654321', 'o.kuznecova@example.com');


INSERT INTO luggage (weight_kg, pieces, status) VALUES
(25.50, 2, 'зарегистрирован'), -- Багаж для Александра Петрова
(18.00, 1, 'зарегистрирован'), -- Багаж для Екатерины Ивановой
(30.00, 3, 'в пути');          -- Багаж для Ольги Кузнецовой


INSERT INTO cars (car_number, train_id, car_type, capacity, build_date, status) VALUES
('001П', (SELECT train_id FROM trains WHERE model = 'ЭП20'), 'плацкарт', 54, '2008-07-01', 'в эксплуатации'),  -- (ЭП20)
('002П', (SELECT train_id FROM trains WHERE model = 'ЭП20'), 'плацкарт', 54, '2008-07-01', 'в эксплуатации'),  -- (ЭП20)
('003К', (SELECT train_id FROM trains WHERE model = 'ЭП20'), 'купе', 36, '2015-09-10', 'в эксплуатации'),      -- (ЭП20)
('004С', (SELECT train_id FROM trains WHERE model = 'ЧС7'), 'сидячий', 62, '2012-04-05', 'в эксплуатации'),    -- (ЧС7)
('005С', (SELECT train_id FROM trains WHERE model = 'ЧС7'), 'сидячий', 62, '2012-04-05', 'в эксплуатации'),    -- (ЧС7)
('006С', (SELECT train_id FROM trains WHERE model = 'ЧС7'), 'сидячий', 62, '2012-04-05', 'в ремонте'),         -- (ЧС7)
('007Р', (SELECT train_id FROM trains WHERE model = 'ЭП20'), 'ресторан', 30, '2010-11-20', 'в эксплуатации'),  -- (ЭП20)
('008Б', (SELECT train_id FROM trains WHERE model = '2ТЭ116У'), 'багажный', 0, '1998-02-14', 'в эксплуатации'),-- (2ТЭ116У - грузовой)
('П01', (SELECT train_id FROM trains WHERE model = 'ЭР2'), 'сидячий', 100, '1975-01-01', 'в эксплуатации');    -- (ЭР2 - пригородный)


INSERT INTO seats (car_id, seat_number, seat_type, is_available, features) VALUES
((SELECT car_id FROM cars WHERE car_number = '001П'), '1', 'нижнее', TRUE, 'У окна, рядом розетка'),
((SELECT car_id FROM cars WHERE car_number = '001П'), '2', 'верхнее', TRUE, 'У прохода'),
((SELECT car_id FROM cars WHERE car_number = '001П'), '3', 'нижнее', TRUE, 'У окна'),
((SELECT car_id FROM cars WHERE car_number = '001П'), '4', 'верхнее', TRUE, 'У прохода'),
((SELECT car_id FROM cars WHERE car_number = '001П'), '37', 'боковое нижнее', TRUE, NULL),  --(Боковые места)
((SELECT car_id FROM cars WHERE car_number = '001П'), '38', 'боковое верхнее', TRUE, NULL), --(Боковые места)
-- Купе 003К (car_id=3)
((SELECT car_id FROM cars WHERE car_number = '003К'), '1', 'нижнее', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = '003К'), '2', 'верхнее', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = '003К'), '3', 'нижнее', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = '003К'), '4', 'верхнее', FALSE, 'Неисправна полка'), -- (Одно место не доступно)
-- Сидячий 004С (car_id=4)
((SELECT car_id FROM cars WHERE car_number = '004С'), '5A', 'у окна', TRUE, 'Розетка'),
((SELECT car_id FROM cars WHERE car_number = '004С'), '5B', 'у прохода', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = '004С'), '6A', 'у окна', TRUE, NULL),
-- Сидячий П01 (car_id=9) - Пригородный
((SELECT car_id FROM cars WHERE car_number = 'П01'), '1', 'у окна', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = 'П01'), '2', 'у прохода', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = 'П01'), '3', 'у окна', TRUE, NULL),
((SELECT car_id FROM cars WHERE car_number = 'П01'), '4', 'у прохода', TRUE, NULL);


INSERT INTO tickets (schedule_id, passenger_id, seat_id, luggage_id, purchase_date, price, ticket_status) VALUES
-- Билет на рейс 758А, плацкарт 001П, место 1
((SELECT schedule_id FROM schedules WHERE train_number = '758А' AND departure_time = '2023-11-25 14:00:00'),
 (SELECT passenger_id FROM passengers WHERE passport_series = '1234' AND passport_number = '567890'),
 (SELECT seat_id FROM seats WHERE car_id = (SELECT car_id FROM cars WHERE car_number = '001П') AND seat_number = '1'),
 1,
 '2023-11-20 10:00:00', 3500.00, 'оплачен'),
-- Билет на рейс 758А, плацкарт 001П, место 37
((SELECT schedule_id FROM schedules WHERE train_number = '758А' AND departure_time = '2023-11-25 14:00:00'),
 (SELECT passenger_id FROM passengers WHERE passport_series = '5678' AND passport_number = '123456'),
 (SELECT seat_id FROM seats WHERE car_id = (SELECT car_id FROM cars WHERE car_number = '001П') AND seat_number = '37'),
 2,
 '2023-11-21 11:00:00', 3200.00, 'оплачен'),
-- Билет на рейс 001М, купе 003К, место 1
((SELECT schedule_id FROM schedules WHERE train_number = '001М' AND departure_time = '2023-11-25 23:00:00'),
 (SELECT passenger_id FROM passengers WHERE passport_series = '3456' AND passport_number = '789012'),
 (SELECT seat_id FROM seats WHERE car_id = (SELECT car_id FROM cars WHERE car_number = '003К') AND seat_number = '1'),
 3,
 '2023-11-22 12:00:00', 4000.00, 'оплачен'),
-- Билет на рейс 002Я, сидячий 004С, место 5A
((SELECT schedule_id FROM schedules WHERE train_number = '002Я' AND departure_time = '2023-11-26 08:00:00'),
 (SELECT passenger_id FROM passengers WHERE passport_series = '1234' AND passport_number = '567890'),
 (SELECT seat_id FROM seats WHERE car_id = (SELECT car_id FROM cars WHERE car_number = '004С') AND seat_number = '5A'),
 NULL, -- Без багажа
 '2023-11-23 15:00:00', 1800.00, 'забронирован'),
-- Билет на рейс 758А, купе 003К, место 3
((SELECT schedule_id FROM schedules WHERE train_number = '758А' AND departure_time = '2023-11-25 14:00:00'),
 (SELECT passenger_id FROM passengers WHERE passport_series = '9012' AND passport_number = '345678'), -- Николай Смирнов
 (SELECT seat_id FROM seats WHERE car_id = (SELECT car_id FROM cars WHERE car_number = '003К') AND seat_number = '3'),
 NULL, -- Без багажа
 '2023-11-24 18:00:00', 4500.00, 'возвращен'),
-- Билет на пригородный рейс 6616, сидячий П01, место 2
((SELECT schedule_id FROM schedules WHERE train_number = '6616' AND departure_time = '2023-11-25 18:30:00'),
 (SELECT passenger_id FROM passengers WHERE passport_series = '3456' AND passport_number = '789012'),
 (SELECT seat_id FROM seats WHERE car_id = (SELECT car_id FROM cars WHERE car_number = 'П01') AND seat_number = '2'),
 NULL, -- Без багажа
 '2023-11-25 10:00:00', 400.00, 'оплачен');