create database IF NOT EXISTS vsusenglish;
use vsusenglish;

create table if not exists account
(
	account_id INT unsigned not null,
    email varchar(100) not null,
    password_hash varchar(255) not null,
    status enum('active', 'inactive', 'banned') not null,
    created_at datetime not null,
    updated_at datetime not null,
    last_login datetime
);
alter table account 
add constraint pk_account Primary Key (account_id),
add constraint email_unique Unique (email);

create table if not exists user_profile
(
	user_id INT unsigned not null,
    account_id INT unsigned not null,
    username varchar(50) not null,
    fullname varchar(100) not null,
    gender enum('Male', 'Female') not null,
    phone varchar(15) not null,
    yob date not null,
    join_date datetime not null,
    avatar_url varchar(255),
    bio varchar(200) Default 'I have no clue',
    contribution_point INT not null Default 0,
    ELO INT not null Default 0
);
alter table user_profile
add constraint pk_user Primary Key (user_id),
add constraint fk_user_account Foreign Key (account_id) References account(account_id),
add constraint account_unique Unique (account_id),
add constraint username_unique Unique (username),
add constraint phone_unique Unique (phone);

create table if not exists role
(
    role_id INT unsigned not null,
    role_name varchar(50) not null
);
alter table role
add constraint pk_role Primary Key (role_id),
add constraint rolename_unique Unique (role_name);

create table if not exists account_role
(
	account_id INT unsigned not null,
    role_id INT unsigned not null
);
alter table account_role
add constraint pk_account_role primary key (account_id, role_id),
add constraint fk_account_role Foreign Key (account_id) References account(account_id),
add constraint fk_role Foreign Key (role_id) References role(role_id);

