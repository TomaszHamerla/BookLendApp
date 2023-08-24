create table books(
    id bigint primary key auto_increment,
    title varchar(100) not null ,
    author varchar(100)not null,
    available BIT
);