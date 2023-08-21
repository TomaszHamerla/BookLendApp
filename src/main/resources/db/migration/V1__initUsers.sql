create table users (
    id int primary key AUTO_INCREMENT,
    first_name varchar(100),
    last_name varchar(100) not null,
    email varchar(100) not null
);