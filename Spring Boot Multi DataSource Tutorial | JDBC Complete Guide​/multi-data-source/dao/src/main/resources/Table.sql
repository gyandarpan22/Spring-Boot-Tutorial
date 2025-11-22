create table employee(
    employee_id    bigint auto_increment primary key,
    employee_name  varchar(100) NOT NULL,
    age            int,
    joining_date   date not null,
    department     enum('DEVELOPER','QA','HR') not null,
    salary         double
    created_date   timestamp not null default current_timestamp,
    modified_date  timestamp not null default current_timestamp on update current_timestamp
)


create table users(
    user_id  bigint auto_increment primary key,
    user_name varchar(100) NOT NULL,
    email varchar(100) unique,
    created_at  timestamp not null default current_timestamp,
    updated_at  timestamp not null default current_timestamp on update current_timestamp
)