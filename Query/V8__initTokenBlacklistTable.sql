use vsusenglish;

CREATE Table token_blacklist
(
    token_id INT unsigned not null auto_increment primary key,
    token varchar(255) not null,
    email varchar(255) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    token_expiry TIMESTAMP not null
);
alter table token_blacklist
    add constraint token_blacklist_token_uindex unique (token),
    add constraint token_blacklist_email_uindex unique (email);