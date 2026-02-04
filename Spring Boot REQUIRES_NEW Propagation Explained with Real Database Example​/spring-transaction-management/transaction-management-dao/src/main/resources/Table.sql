create table Account(
    account_id    bigint auto_increment primary key,
    name  varchar(100) NOT NULL,
    amount            DECIMAL(10,2)
)