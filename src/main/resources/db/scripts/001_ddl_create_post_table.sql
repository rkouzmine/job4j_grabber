create table post (
    id serial primary key,
    name varchar(100) not null,
    text text not null,
    link text unique not null,
    created Timestamp not null
);