-- auto-generated definition
create table categories
(
    category_id   int         not null
        primary key,
    category_name varchar(45) not null
);

-- auto-generated definition
create table roles
(
    role_id   int         not null
        primary key,
    role_name varchar(45) not null
);

-- auto-generated definition
create table comments
(
    comment_id           bigint(11) auto_increment
        primary key,
    user_id              bigint(11)                          null,
    lifehack_id          bigint(11)                          not null,
    comment_content      varchar(255)                        not null,
    comment_likes_amount int       default 0                 not null,
    post_date            timestamp default CURRENT_TIMESTAMP null,
    constraint fk_comment_lifehack_id
        foreign key (lifehack_id) references lifehacks (lifehack_id),
    constraint fk_comment_user_id
        foreign key (user_id) references users (user_id)
            on delete cascade
);

create index fk_comment_lifehack_id_idx
    on comments (lifehack_id);

create index fk_comment_user_id_idx
    on comments (user_id);

-- auto-generated definition
create table lifehacks
(
    lifehack_id           bigint(11) auto_increment
        primary key,
    author_user_id        bigint(11)                          not null,
    lifehack_name         varchar(100)                        not null,
    lifehack_content      mediumtext                          not null,
    excerpt               varchar(60)                         not null,
    publication_date      timestamp default CURRENT_TIMESTAMP null,
    category_id           int                                 not null,
    lifehack_likes_amount int       default 0                 not null,
    picture               mediumblob                          not null,
    constraint fk_author_user_id
        foreign key (author_user_id) references users (user_id),
    constraint fk_category_id
        foreign key (category_id) references categories (category_id)
            on update cascade
);

create index fk_author_user_id_idx
    on lifehacks (author_user_id);

create index fk_category_id_idx
    on lifehacks (category_id);

-- auto-generated definition
create table user_like_comments
(
    user_id    bigint(11) not null,
    comment_id bigint(11) not null,
    constraint fk_id_comment
        foreign key (comment_id) references comments (comment_id)
            on delete cascade,
    constraint fk_id_user
        foreign key (user_id) references users (user_id)
            on delete cascade
);

create index fk_id_comment_idx
    on user_like_comments (comment_id);

create index fk_user_id_idx
    on user_like_comments (user_id);

-- auto-generated definition
create table user_like_lifehacks
(
    user_id     bigint(11) not null,
    lifehack_id bigint(11) not null,
    constraint fk_lifehack_id
        foreign key (lifehack_id) references lifehacks (lifehack_id)
            on delete cascade,
    constraint fk_user_id
        foreign key (user_id) references users (user_id)
            on delete cascade
);

create index fk_lifehack_id_idx
    on user_like_lifehacks (lifehack_id);

create index fk_user_id_idx
    on user_like_lifehacks (user_id);


-- auto-generated definition
create table users
(
    user_id       bigint(11) auto_increment
        primary key,
    role_id       int                not null,
    firstname     varchar(45)        not null,
    lastname      varchar(45)        not null,
    nickname      varchar(45)        not null,
    email         varchar(61)        not null,
    password_hash varchar(256)       not null,
    activated     enum ('No', 'Yes') not null,
    constraint email_UNIQUE
        unique (email),
    constraint nickname_UNIQUE
        unique (nickname),
    constraint fk_role_id
        foreign key (role_id) references roles (role_id)
);

create index fk_role_id_idx
    on users (role_id);

