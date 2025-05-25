create table employee(
    employee_id    bigint auto_increment primary key,
    employee_name  varchar(100) NOT NULL,
    age            int,
    joining_date   date not null,
    department     enum('DEVELOPER','QA','HR') not null,
    created_date   timestamp not null default current_timestamp,
    modified_date  timestamp not null default current_timestamp on update current_timestamp
)