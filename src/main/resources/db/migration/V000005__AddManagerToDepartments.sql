ALTER TABLE departments ADD COLUMN manager_id INTEGER;

ALTER TABLE departments
ADD CONSTRAINT fk_department_manager
FOREIGN KEY (manager_id) REFERENCES employees(employee_id) ON DELETE SET NULL;

UPDATE departments SET manager_id = (SELECT employee_id FROM employees WHERE last_name = 'Иванов') WHERE name = 'Отдел эксплуатации';
UPDATE departments SET manager_id = (SELECT employee_id FROM employees WHERE last_name = 'Ремонтников') WHERE name = 'Отдел ремонта и обслуживания';
UPDATE departments SET manager_id = (SELECT employee_id FROM employees WHERE last_name = 'Кадровикова') WHERE name = 'Отдел кадров';