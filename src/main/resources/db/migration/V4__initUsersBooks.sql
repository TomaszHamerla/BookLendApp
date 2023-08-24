CREATE TABLE users_books
(
    user_id BIGINT,
    books_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (books_id) REFERENCES books (id)
);


