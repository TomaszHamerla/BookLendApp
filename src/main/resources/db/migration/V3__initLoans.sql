create table loans(
    id bigint primary key auto_increment,
    loan_date date,
    deadline date,
    user_id bigint,
    book_id bigint

);
alter table loans
add foreign key (user_id)references users(id);
alter table loans
add foreign key (book_id)references books(id);