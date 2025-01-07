INSERT INTO member (user_id, password, name, phone_address, email, sex, referral_id, level, role, from_social, init, deleted, reg_date, dtype)
VALUES
    (UUID(), '$2a$10$J03L0oKiIDC5mhdYxzOTresMpd9gMfqbJakKr.iHAsgUbq/To2VZO', '관리자', '010-1234-1234', 'admin@gmail.com', 'MALE', null, 1, 'ROLE_ADMIN', 0, 1, 0, NOW(), 'A'),
    ('c99fd58f-b0ae-11ef-89d8-0242ac140003', '$2a$10$/essrJvIRI8sf52JKG/YHO.5bv6JOqmk8UFD.kFK8a12WtQZ.t2sK', '김학생', '010-1234-1234', 'student@gmail.com', 'MALE', null, 1, 'ROLE_STUDENT', 0, 1, 0, NOW(), 'S'),
    ('98a10847-ad7e-11ef-8e5c-0242ac140002', '$2a$10$J03L0oKiIDC5mhdYxzOTresMpd9gMfqbJakKr.iHAsgUbq/To2VZO', '김강사', '010-1234-1234', 'teacher@gmail.com', 'MALE', null, 1, 'ROLE_TEACHER', 0, 1, 0, NOW(), 'T'),
    ('c99fd83e-b0ae-11ef-89d8-0242ac140003', '$2a$10$tPBgYrrCy8kKo7G.w5uVJeCcGPaCDOC80oV5qK2PE68THsjk443wy', '김학생 학부모', '010-1234-1234', 'parents@gmail.com', 'FEMALE', null, 1, 'ROLE_PARENTS', 0, 1, 0, NOW(), 'P'),
    ('add9fa2e-92c9-48ee-adb7-46c307ca8778', '$2a$10$nRCXAFC8IOh78cwfh9v57uJt1NzF8vLgWzn9.OOXgPx3hIk4e/2sq', '조강사', '010-1234-1234', 'otherteacher@gmail.com', 'FEMALE', null, 1, 'ROLE_TEACHER', 0, 1, 0, NOW(), 'T'),
    ('e8445639-917a-4396-8aaa-4a68dd11e4c7', '$2a$10$kdG9XoA8h0J7UirQ1xuUfuzVfa/BgGzZtEjmPc063.vrevHZfM6oK', '조학생', '010-1234-1234', 'otherstudent@gmail.com', 'MALE', null, 1, 'ROLE_STUDENT', 0, 1, 0, NOW(), 'S'),
    ('c2862de7-e8ef-4aa8-bf7d-711cd712279b', '$2a$10$S3rRiFdZMWjtaOsKeD6HxOsdq9pJqvlc6vI1wofESR1s13RUkj0PG', '조학생 학부모', '010-1234-1234', 'otherparents@gmail.com', 'FEMALE', null, 1, 'ROLE_PARENTS', 0, 1, 0, NOW(), 'P');


INSERT INTO teacher
VALUES
    ('98a10847-ad7e-11ef-8e5c-0242ac140002', 'GRADUATION', '한국대학교', '수학'),
    ('add9fa2e-92c9-48ee-adb7-46c307ca8778', 'GRADUATION', '행복대학교', '영어');

INSERT INTO official_profile (USER_ID, CONTENT)
VALUES
	('98a10847-ad7e-11ef-8e5c-0242ac140002', '<p>공식 프로필 페이지를 설정해주세요.</p>'),
	('add9fa2e-92c9-48ee-adb7-46c307ca8778', '<p>공식 프로필 페이지를 설정해주세요.</p>');

INSERT INTO student
VALUES
    ('c99fd58f-b0ae-11ef-89d8-0242ac140003', '한국고등학교', 'H1'),
    ('e8445639-917a-4396-8aaa-4a68dd11e4c7', '강남고등학교', 'H3');

INSERT INTO classroom(classroom_id, teacher_id, title, description, capacity, thumbnail, reg_date, created_by)
VALUES
	('98a12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002', '이상한수학', '수학을 잘 하고 싶은 사람들 모두 모여라', 20, 'http://어딘가', NOW(), '98a10847-ad7e-11ef-8e5c-0242ac140002'),
	('76b12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002', '이상한과학', '과학을 잘 하고 싶은 사람들 모두 모여라', 20, 'http://어딘가', NOW(), '98a10847-ad7e-11ef-8e5c-0242ac140002');

INSERT INTO faq (faq_id, title, content, classroom_id, created_by, reg_date)
VALUES
	(1, '동영상 시청은 어떻게 하나요?', 'A 화면 어딘가에 박혀있는 B라는 버튼을 누르면 어디로 navigation 되는데 ~~~', '98a12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002',  NOW());

INSERT INTO class_inquiry (classroom_id, title, content, visibility, reg_date, created_by)
VALUES
('98a12345-ad7e-11ef-8e5c-0242ac140002',
'수업 커리큘럼 내 과제는 어떻게 진행되는 건가요?',
'안녕하세요. 이번 클래스에 참가하고 싶은 학생입니다. 수업 내에 과제 관련된 내용이 포함되어 있던데, 이것에 대한 자세한 운영 계획을 들어볼 수 있을까요?',
'PRIVATE', NOW(), 'c99fd58f-b0ae-11ef-89d8-0242ac140003');

INSERT INTO reply (comment_id, content, visibility, created_by, reg_date, parent_comment_id, class_inquiry_id)
VALUES
(1, '저희 강의는 최첨단 강의어서 모두가 만족할 수 있습니다.', 'PUBLIC', '98a10847-ad7e-11ef-8e5c-0242ac140002', NOW(), NULL, 1),
(2, '그렇군요, 알겠습니다.', 'PUBLIC', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW(), 1, 1),
(3, '비밀 댓글입니다. 비밀.', 'PRIVATE', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW(), NULL, 1);

INSERT INTO enrollment
VALUES
(1, '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'PERMITTED' ,'50점', '100 이요', '없습니다', '화이팅', NOW(), NULL, NULL, NULL, NULL),
(2, '76b12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'PERMITTED' ,'50점', '100 이요', '없습니다', '화이팅', NOW(), NULL, NULL, NULL, NULL);

INSERT INTO record_lecture
(id, classroom_id, created_by, del_date, deleted_by, description, mod_date, modified_by, order_num, playtime, reg_date, title, url)
VALUES
(1, '98a12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002', NULL, NULL, 'test', NULL, NULL, NULL, 0, NOW(), 'test', '/videos/1038052017');


