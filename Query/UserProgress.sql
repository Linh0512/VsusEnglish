use vsusenglish;

create table if not exists user_lesson_progress
(
	user_id INT unsigned not null,
	lesson_id INT unsigned not null,
    status ENUM('completed', 'uncompleted') not null,
    last_question_id INT unsigned not null,
    updated_at datetime not null,
    completed_at datetime
);
alter table user_lesson_progress
add constraint pk_lesson_progress primary key (user_id, lesson_id),
add constraint fk_lesson_lp foreign key (lesson_id) references lesson(lesson_id),
add constraint fk_user_lp foreign key (user_id) references user_profile(user_id);

create table user_test_progress
(
	user_id INT unsigned not null,
	test_id INT unsigned not null,
    status ENUM('completed', 'uncompleted') not null,
    last_question_id INT unsigned not null,
    updated_at datetime not null,
    completed_at datetime
);
alter table user_test_progress
add constraint pk_test_progress primary key (user_id, test_id),
add constraint fk_test_tp foreign key (test_id) references test(test_id),
add constraint fk_user_tp foreign key (user_id) references user_profile(user_id);

use vsusenglish;
create table user_test_question_attempt
(
	attempt_id INT unsigned not null,
	user_id INT unsigned not null,
    test_id INT unsigned not null,
    question_id INT unsigned not null,
    user_answer varchar(500) not null,
    correct_value varchar(500) not null,
    is_correct boolean not null,
    answered_at datetime not null
);
alter table user_test_question_attempt
add constraint pk_test_attempt_id primary key (attempt_id),
add constraint fk_test_tqa foreign key (test_id) references test(test_id),
add constraint fk_question_tqa foreign key (question_id) references question(question_id),
add constraint fk_user_tqa foreign key (user_id) references user_profile(user_id);
use vsusenglish;

create table user_lesson_question_attempt
(
	attempt_id INT unsigned not null,
	user_id INT unsigned not null,
    lesson_id INT unsigned not null,
    question_id INT unsigned not null,
    user_answer varchar(500) not null,
    correct_value varchar(500) not null,
    is_correct boolean not null,
    answered_at datetime not null
);
alter table user_lesson_question_attempt
add constraint pk_lesson_attempt_id primary key (attempt_id),
add constraint fk_lesson_lqa foreign key (lesson_id) references lesson(lesson_id),
add constraint fk_question_lqa foreign key (question_id) references question(question_id),
add constraint fk_user_lqa foreign key (user_id) references user_profile(user_id);




