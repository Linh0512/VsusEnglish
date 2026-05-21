use vsusenglish;

create table matching
(
	match_id INT unsigned not null,
    test_id INT unsigned not null,
    status ENUM('ONGOING', 'FINISHED', 'CANCELLED'),
    start_at datetime,
    end_at datetime,
    time_limit_sec INT unsigned not null
);
alter table matching
add constraint pk_matching primary key (match_id),
add constraint fk_test_matching foreign key (test_id) references test(test_id),
add constraint check_time_end check (end_at IS NULL OR start_at <= end_at),
add constraint check_time_limit check (time_limit_sec > 0);

create table match_participant
(
	match_id INT unsigned not null,
    user_id INT unsigned not null,
    betting_amount INT unsigned not null,
    elo_before INT unsigned not null,
    elo_after INT unsigned null,
	result ENUM('Win', 'Lose', 'Draw') null,
    joined_at datetime not null
);
alter table match_participant
add constraint pk_match_participant primary key (match_id, user_id),
add constraint fk_match_mp foreign key (match_id) references matching(match_id),
add constraint fk_user_mp foreign key (user_id) references user_profile(user_id);

create table matchmaking_queue
(
	queue_id INT unsigned not null,
    user_id INT unsigned not null,
    match_id INT unsigned null,
    elo_snapshot INT unsigned not null,
    betting_amount INT unsigned not null,
	status ENUM('WAITING','MATCHED','CANCELLED','TIMEOUT'),
    created_at datetime not null,
    matched_at datetime null,
    expires_at datetime null
);
alter table matchmaking_queue
add constraint pk_matchmaking_queue primary key (queue_id),
add constraint fk_match_queue foreign key (match_id) references matching(match_id),
add constraint fk_user_queue foreign key (user_id) references user_profile(user_id);

create table match_answer
(
	match_id INT unsigned not null,
    user_id INT unsigned not null,
    question_id INT unsigned not null,
    selected_option_id INT unsigned not null,
    is_correct bool not null,
    response_time_ms INT unsigned not null,
    answered_at datetime not null
);
alter table match_answer
add constraint pk_match_answer primary key (match_id, user_id, question_id),
add constraint fk_match_ma foreign key (match_id) references matching(match_id),
add constraint fk_user_ma foreign key (user_id) references user_profile(user_id),
add constraint fk_question_ma foreign key (question_id) references question(question_id),
add constraint fk_slt_option_ma foreign key (selected_option_id, question_id) references question_option(option_id, question_id),
add constraint check_respone_time_ma check (response_time_ms >= 0);

