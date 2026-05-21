use vsusenglish;

create table post
(
	post_id INT unsigned not null,
    user_id INT unsigned not null,
    title varchar(255) not null,
    content text not null,
    post_type varchar(50) not null,
    status ENUM('ACTIVE','HIDDEN','DELETED') not null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table post
add constraint pk_post primary key (post_id),
add constraint fk_post_user foreign key (user_id) references user_profile(user_id),
add constraint check_post_time check (created_at <= updated_at);

create table user_comment
(
	comment_id INT unsigned not null,
    post_id INT unsigned not null,
    user_id INT unsigned not null,
    parent_comment_id INT unsigned null,
    content text not null,
    status ENUM('ACTIVE','HIDDEN','DELETED') not null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table user_comment
add constraint pk_comment primary key (comment_id),
add constraint fk_comment_post foreign key (post_id) references post(post_id),
add constraint fk_comment_user foreign key (user_id) references user_profile(user_id),
add constraint unique_parent_comment_id unique (parent_comment_id),
add constraint check_comment_time check (created_at <= updated_at);

alter table user_comment
add constraint fk_comment_parent foreign key (parent_comment_id) references user_comment(comment_id);

create table reaction_type
(
	reaction_type_id INT unsigned not null,
    reaction_code varchar(50) not null
);
alter table reaction_type
add constraint pk_reaction_type primary key (reaction_type_id),
add constraint uq_reaction_code unique (reaction_code);

create table post_reaction
(
	post_id INT unsigned not null,
    user_id INT unsigned not null,
    reaction_type_id INT unsigned not null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table post_reaction
add constraint pk_post_reaction primary key (post_id, user_id),
add constraint fk_post_reaction_post foreign key (post_id) references post(post_id),
add constraint fk_post_reaction_user foreign key (user_id) references user_profile(user_id),
add constraint fk_post_reaction_type foreign key (reaction_type_id) references reaction_type(reaction_type_id);

create table comment_reaction
(
	comment_id INT unsigned not null,
    user_id INT unsigned not null,
    reaction_type_id INT unsigned not null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table comment_reaction
add constraint pk_comment_reaction primary key (comment_id, user_id),
add constraint fk_comment_reaction_comment foreign key (comment_id) references user_comment(comment_id),
add constraint fk_comment_reaction_user foreign key (user_id) references user_profile(user_id),
add constraint fk_comment_reaction_type foreign key (reaction_type_id) references reaction_type(reaction_type_id);

create table post_report
(
	post_report_id INT unsigned not null,
    post_id INT unsigned not null,
    reporter_user_id INT unsigned not null,
    reason varchar(255) not null,
    status ENUM('PENDING','RESOLVED','REJECTED') not null,
    handled_by INT unsigned null,
    handled_at datetime null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table post_report
add constraint pk_post_report primary key (post_report_id),
add constraint fk_post_report_post foreign key (post_id) references post(post_id),
add constraint fk_post_report_user foreign key (reporter_user_id) references user_profile(user_id),
add constraint fk_post_report_admin foreign key (handled_by) references user_profile(user_id);

create table comment_report
(
	cmt_report_id INT unsigned not null,
    comment_id INT unsigned not null,
    reporter_user_id INT unsigned not null,
    reason varchar(255) not null,
    status ENUM('PENDING','RESOLVED','REJECTED') not null,
    handled_by INT unsigned null,
    handled_at datetime null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table comment_report
add constraint pk_comment_report primary key (cmt_report_id),
add constraint fk_comment_report_comment foreign key (comment_id) references user_comment(comment_id),
add constraint fk_comment_report_user foreign key (reporter_user_id) references user_profile(user_id),
add constraint fk_comment_report_admin foreign key (handled_by) references user_profile(user_id);