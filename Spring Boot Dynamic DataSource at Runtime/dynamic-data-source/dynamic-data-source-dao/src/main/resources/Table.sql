create table employee(
    employee_id    bigint auto_increment primary key,
    employee_name  varchar(100) NOT NULL,
    age            int,
    joining_date   date not null,
    department     enum('DEVELOPER','QA','HR') not null,
    salary         double,
    created_date   timestamp not null default current_timestamp,
    modified_date  timestamp not null default current_timestamp on update current_timestamp
)

create table tenant_db_config_detail(
        tenant_id VARCHAR(50) PRIMARY KEY,
        jdbc_url VARCHAR(255),
        username VARCHAR(50),
        password VARCHAR(50),
        driver_class_name VARCHAR(50)
)