use vsusenglish;

create table conversation
(
	conversation_id INT unsigned not null,
    created_at datetime not null,
    updated_at datetime not null,
    last_message_id INT unsigned null
);
alter table conversation
add constraint pk_conversation primary key (conversation_id);

create table conversation_participant
(
	conversation_id INT unsigned not null,
    user_id INT unsigned not null,
    joined_at datetime not null
);
alter table conversation_participant
add constraint pk_conversation_participant primary key (conversation_id, user_id);
alter table conversation_participant
add constraint fk_cp_conversation foreign key (conversation_id) references conversation(conversation_id),
add constraint fk_cp_user foreign key (user_id) references user_profile(user_id),
add constraint unique_user_conversation unique (user_id, conversation_id);

create table message
(
	message_id INT unsigned not null,
    conversation_id INT unsigned not null,
    sender_user_id INT unsigned not null,
    content text not null,
    status ENUM('SENT','DELETED') not null,
    created_at datetime not null,
    updated_at datetime not null
);
alter table message
add constraint pk_message primary key (message_id),
add constraint fk_message_conversation foreign key (conversation_id) references conversation(conversation_id),
add constraint fk_message_user foreign key (sender_user_id) references user_profile(user_id);
alter table conversation
add constraint fk_conversation_last_message foreign key (last_message_id) references message(message_id);

create table message_read
(
	message_id INT unsigned not null,
    user_id INT unsigned not null,
    read_at datetime not null
);
alter table message_read
add constraint pk_message_read primary key (message_id, user_id),
add constraint fk_mr_message foreign key (message_id) references message(message_id),
add constraint fk_mr_user foreign key (user_id) references user_profile(user_id);

