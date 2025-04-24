drop table if exists authors cascade;
create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

drop table if exists genres cascade;
create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

drop table if exists books cascade;
create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

drop table if exists comments cascade;
create table comments (
    id bigserial,
    text varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);