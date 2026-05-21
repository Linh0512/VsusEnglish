use vsusenglish;

create table category
(
	category_id INT unsigned not null,
    category_name varchar(100) not null
--     type varchar(50) not null
);
alter table category
add constraint pk_category primary key (category_id),
add constraint category_name_unique unique (category_name);
-- add constraint type_unique unique (type);

create table if not exists lesson
(
	lesson_id INT unsigned not null,
    category_id INT unsigned not null,
    lesson_title varchar(200) not null,
    description varchar(255) not null,
    level ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') not null,
    created_by INT unsigned not null,
	created_at datetime not null,
    updated_at datetime not null
);
alter table lesson
add constraint pk_lesson primary key (lesson_id),
add constraint fk_lesson_created_by foreign key (created_by) references account(account_id),
add constraint fk_lesson_category foreign key (category_id) references category(category_id),
add constraint lesson_title_unique unique (lesson_title);

create table test
(
	test_id INT unsigned not null,
    test_title varchar(200) not null,
	level ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') not null,
    created_by INT unsigned not null,
	created_at datetime not null,
    updated_at datetime not null
);
alter table test
add constraint pk_test primary key (test_id),
add constraint fk_test_created_by foreign key (created_by) references account(account_id),
add constraint test_title_unique unique (test_title);

create table question
(
	question_id INT unsigned not null,
    question_content varchar(500) not null,
    explanation varchar(1000) not null,
	level ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') not null,
    created_by INT unsigned not null,
	created_at datetime not null,
    updated_at datetime not null
);
alter table question
add constraint pk_question primary key (question_id),
add constraint fk_question_created_by foreign key (created_by) references account(account_id),
add constraint question_content_unique unique (question_content);

create table vocab
(
	vocab_id INT unsigned not null,
    word varchar(50) not null,
    word_form ENUM('verb', 'noun', 'adj', 'adv') not null,
	level ENUM('A1', 'A2', 'B1', 'B2', 'C1', 'C2') not null,
    meaning varchar(500) not null,
    example_sentence varchar(1000) not null,
    created_by INT unsigned not null,
	created_at datetime not null,
    updated_at datetime not null
);
alter table vocab
add constraint pk_vocab primary key (vocab_id),
add constraint fk_vocab_created_by foreign key (created_by) references account(account_id),
add constraint word_unique unique (word);

create table category_lesson
(
	category_id INT unsigned not null,
    lesson_id INT unsigned not null,
    lesson_order INT unsigned not null
);
alter table category_lesson
add constraint pk_category_lesson primary key (category_id, lesson_id),
add constraint fk_category_cl foreign key (category_id) references category(category_id),
add constraint fk_lesson_cl foreign key (lesson_id) references lesson(lesson_id),
add constraint lesson_order_unique unique (category_id, lesson_order);

create table category_test
(
	category_id INT unsigned not null,
    test_id INT unsigned not null,
    test_order INT unsigned not null
);
alter table category_test
add constraint pk_category_test primary key (category_id, test_id),
add constraint fk_category_ct foreign key (category_id) references category(category_id),
add constraint fk_test_ct foreign key (test_id) references test(test_id),
add constraint test_order_unique unique (category_id, test_order);

create table lesson_question
(
    lesson_id INT unsigned not null,
	question_id INT unsigned not null,
    lesson_question_order INT unsigned not null
);
alter table lesson_question
add constraint pk_lesson_question primary key (lesson_id, question_id),
add constraint fk_question_lq foreign key (question_id) references question(question_id),
add constraint fk_lesson_lq foreign key (lesson_id) references lesson(lesson_id),
add constraint lesson_question_order_unique unique (lesson_id, lesson_question_order);

create table lesson_vocab
(
    lesson_id INT unsigned not null,
	vocab_id INT unsigned not null,
    vocab_order INT unsigned not null
);
alter table lesson_vocab
add constraint pk_lesson_vocab primary key (lesson_id, vocab_id),
add constraint fk_vocab_lv foreign key (vocab_id) references vocab(vocab_id),
add constraint fk_lesson_lv foreign key (lesson_id) references lesson(lesson_id),
add constraint vocab_order_unique unique (lesson_id, vocab_order);

create table test_question
(
    test_id INT unsigned not null,
	question_id INT unsigned not null,
    question_order INT unsigned not null
);
alter table test_question
add constraint pk_test_question primary key (test_id, question_id),
add constraint fk_test_tq foreign key (test_id) references test(test_id),
add constraint fk_question_tq foreign key (question_id) references question(question_id),
add constraint test_question_order_unique unique (test_id, question_order);

create table question_option
(
	option_id INT unsigned not null,
	question_id INT unsigned not null,
    option_content varchar(500) not null,
    is_correct bool not null,
    option_order INT not null,
	created_at datetime not null,
    updated_at datetime not null
);
alter table question_option
add constraint pk_question_option primary key (option_id),
add constraint fk_question_qo foreign key (question_id) references question(question_id),
add constraint option_order_unique unique (question_id, option_order),
add constraint unique_qo unique (option_id, question_id);