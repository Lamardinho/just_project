--=============================================  1  ==========================================================
CREATE TABLE student
(
    student_id serial,
    first_name varchar,
    last_name  varchar,
    birthday   date,
    phone      varchar
);

CREATE TABLE cathedra
(
    cathedra_id   serial,
    cathedra_name varchar,
    dean          varchar
);

ALTER TABLE student
    ADD COLUMN middle_name varchar;
ALTER TABLE student
    ADD COLUMN rating float;
ALTER TABLE student
    ADD COLUMN enrolled date;

ALTER TABLE student
    DROP COLUMN middle_name;

ALTER TABLE cathedra
    RENAME TO chair;
ALTER TABLE chair
    RENAME cathedra_id TO chair_id;
ALTER TABLE chair
    RENAME cathedra_name TO chair_name;

ALTER TABLE student
    ALTER COLUMN first_name SET DATA TYPE varchar(64);
ALTER TABLE student
    ALTER COLUMN last_name SET DATA TYPE varchar(64);
ALTER TABLE student
    ALTER COLUMN phone SET DATA TYPE varchar(30);

CREATE TABLE faculty
(
    faculty_id   serial,
    faculty_name varchar
);

INSERT INTO faculty
VALUES ('faculty1'),
       ('faculty2'),
       ('faculty3');

SELECT *
FROM faculty;

TRUNCATE TABLE faculty RESTART IDENTITY;

DROP TABLE faculty;
--==========================================  03-Primary Key.sql  ======================================================
DROP TABLE if exists chair;
CREATE TABLE if not exists chair
(
    cathedra_id serial PRIMARY KEY,
    chair_name  varchar,
    dean        varchar
);

INSERT INTO chair
VALUES (1, 'name', 'dean');

--no duplicates
INSERT INTO chair
VALUES (1, 'name', 'dean');

--no NULLs
INSERT INTO chair
VALUES (NULL, 'name', 'dean');

--only UNIQUE NOT NULLs
INSERT INTO chair
VALUES (2, 'name', 'dean');

--equivalent (almost) to:
DROP TABLE if exists chair;
CREATE TABLE if not exists chair
(
    cathedra_id serial UNIQUE NOT NULL,
    chair_name  varchar,
    dean        varchar
);
ALTER TABLE chair
    DROP CONSTRAINT chair_cathedra_id_key;
alter table chair
    add primary key (cathedra_id);

select constraint_name
from information_schema.key_column_usage
where table_name = 'chair'
  and table_schema = 'public'
  and column_name = 'cathedra_id';

ALTER TABLE chair
    ADD PRIMARY KEY (chair_id);
--==========================================  04-Foreign Key.sql  ======================================================
drop table if exists publisher;
drop table if exists book;

CREATE TABLE if not exists public.publisher
(
    publisher_id   integer      NOT NULL,
    publisher_name varchar(128) NOT NULL,
    address        text         NOT NULL,

    CONSTRAINT PK_publisher_id PRIMARY KEY (publisher_id)
);

CREATE TABLE public.book
(
    book_id      integer               NOT NULL,
    title        text                  NOT NULL,
    isbn         character varying(32) NOT NULL,
    publisher_id integer               NOT NULL,

    CONSTRAINT PK_book_book_id PRIMARY KEY (book_id),
    constraint fk_book_publisher_id FOREIGN KEY (publisher_id) references publisher (publisher_id)
);

--добавим немного данных в publisher
INSERT INTO publisher
VALUES (1, 'Everyman''s Library', 'NY'),
       (2, 'Oxford University Press', 'NY'),
       (3, 'Grand Central Publishing', 'Washington'),
       (4, 'Simon & Schuster', 'Chicago');

--без FK мы можем пихать любые значения
INSERT INTO book
VALUES (1, 'The Diary of a Young Girl', '0199535566', 10); -- Everyman's Library

SELECT *
FROM book;

TRUNCATE TABLE book;

ALTER TABLE book
    ADD CONSTRAINT fk_books_publisher FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id);

-- теперь всякую дичь на вставить
insert into book
values (1, 'The Diary of a Young Girl', '0199535566', 10);

--если хотим сразу задать ограничение при создании таблицы
DROP TABLE book;

CREATE TABLE public.book
(
    book_id      integer               NOT NULL,
    title        text                  NOT NULL,
    isbn         character varying(32) NOT NULL,
    publisher_id integer               NOT NULL,

    CONSTRAINT PK_book_book_id PRIMARY KEY (book_id),
    CONSTRAINT FK_book_publisher FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id)
);

--если хотим удалить ограничение
ALTER TABLE book
    DROP CONSTRAINT FK_book_publisher;
--=================================================  05-CHECK.sql  =================================================
DROP TABLE IF EXISTS book;

CREATE TABLE if not exists public.book
(
    book_id      integer               NOT NULL,
    title        text                  NOT NULL,
    isbn         character varying(32) NOT NULL,
    publisher_id integer               NOT NULL,

    CONSTRAINT PK_book_book_id PRIMARY KEY (book_id)
);

ALTER TABLE book
    ADD COLUMN price decimal
        CONSTRAINT CHK_book_price CHECK (price > 0);

INSERT INTO book
VALUES (1, 'title', 'isbn', 1, -1);
--============================================  08-sequences and tables.sql  ===========================================
DROP TABLE IF EXISTS book;

CREATE TABLE public.book
(
    book_id int NOT NULL,
    title text NOT NULL,
    isbn varchar(32) NOT NULL,
    publisher_id int NOT NULL,

    CONSTRAINT PK_book_book_id PRIMARY KEY(book_id)
);

CREATE SEQUENCE IF NOT EXISTS book_book_id_seq
    START WITH 1 OWNED BY book.book_id;

-- doesn't work
INSERT INTO book (title, isbn, publisher_id)
VALUES ('title', 'isbn', 1);

--we need to set default
ALTER TABLE book
    ALTER COLUMN book_id SET DEFAULT nextval('book_book_id_seq');

--now should work
INSERT INTO book (title, isbn, publisher_id)
VALUES ('title', 'isbn', 1);

SELECT *
FROM book;

INSERT INTO book (title, isbn, publisher_id)
VALUES ('title2', 'isbn2', 1);

SELECT *
FROM book;

--BTW, we need the list of columns if we skip some of them inserting values
--so the following doesn't work
INSERT INTO book
VALUES ('title2', 'isbn2', 1);

--returning
INSERT INTO book (title, isbn, publisher_id)
VALUES ('title3', 'isbn3', 1)
RETURNING book_id;
--============================================     ===========================================