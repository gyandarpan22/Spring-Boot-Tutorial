create table department
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE employee
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    age          INT,
    joining_date DATE         NOT NULL,
    department_id BIGINT,
    salary DOUBLE,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES department (id)
);

CREATE TABLE project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(150)
);

CREATE TABLE employee_project (
    employee_id BIGINT,
    project_id BIGINT,
    PRIMARY KEY (employee_id, project_id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id)  REFERENCES employee(id) ON DELETE CASCADE,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);


CREATE TABLE bank_detail
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    bank_name      VARCHAR(255) NOT NULL,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    ifsc_code      VARCHAR(50)  NOT NULL,
    employee_id    BIGINT UNIQUE,
    CONSTRAINT fk_employee_bank_detail FOREIGN KEY (employee_id) REFERENCES employee (id)
);

