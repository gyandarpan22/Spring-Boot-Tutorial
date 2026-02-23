CREATE TABLE employee
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    age          INT,
    joining_date DATE         NOT NULL,
    department   ENUM('DEVELOPER','QA','HR') NOT NULL,
    salary DOUBLE
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
