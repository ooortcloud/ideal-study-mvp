-- idealstudy 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS idealstudy;

-- 관리자 계정 생성
CREATE USER IF NOT EXISTS 'manager'@'%' IDENTIFIED BY 'manager';

-- 관리자 권한 부여
GRANT ALL PRIVILEGES ON idealstudy.* TO 'manager'@'%';

-- 권한 변경 사항 적용
FLUSH PRIVILEGES;

USE idealstudy;

-- member 테이블 생성
CREATE TABLE IF NOT EXISTS member (
    user_id CHAR(36) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    phone_address VARCHAR(15),
    email VARCHAR(100) NOT NULL UNIQUE,
    sex ENUM('MALE', 'FEMALE'),
    referral_id CHAR(36),
    level INT DEFAULT 0,
    role ENUM('ROLE_GUEST', 'ROLE_STUDENT', 'ROLE_TEACHER', 'ROLE_PARENTS', 'ROLE_ADMIN') NOT NULL,  -- 수정된 부분
    from_social TINYINT DEFAULT 0,
    init TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0,
    mod_date datetime(6),
    reg_date datetime(6) NOT NULL,
    dtype varchar(31) NOT NULL,
    created_by varchar(255),
    del_date datetime(6),
    deleted_by varchar(255),
    modified_by varchar(255),
    introduction varchar(200) default '안녕하세요. 잘 부탁드립니다.',
    image_data longblob
    );

-- 멤버 더미 데이터 삽입
INSERT INTO member (user_id, password, name, phone_address, email, sex, referral_id, level, role, from_social, init, deleted, reg_date, dtype)
VALUES
    (UUID(), '$2a$10$J03L0oKiIDC5mhdYxzOTresMpd9gMfqbJakKr.iHAsgUbq/To2VZO', '관리자', '010-1234-1234', 'admin@gmail.com', 'MALE', null, 1, 'ROLE_ADMIN', 0, 1, 0, NOW(), 'A'),
    ('c99fd58f-b0ae-11ef-89d8-0242ac140003', '$2a$10$/essrJvIRI8sf52JKG/YHO.5bv6JOqmk8UFD.kFK8a12WtQZ.t2sK', '김학생', '010-1234-1234', 'student@gmail.com', 'MALE', null, 1, 'ROLE_STUDENT', 0, 1, 0, NOW(), 'S'),
    ('98a10847-ad7e-11ef-8e5c-0242ac140002', '$2a$10$J03L0oKiIDC5mhdYxzOTresMpd9gMfqbJakKr.iHAsgUbq/To2VZO', '김강사', '010-1234-1234', 'teacher@gmail.com', 'MALE', null, 1, 'ROLE_TEACHER', 0, 1, 0, NOW(), 'T'),
    ('c99fd83e-b0ae-11ef-89d8-0242ac140003', '$2a$10$tPBgYrrCy8kKo7G.w5uVJeCcGPaCDOC80oV5qK2PE68THsjk443wy', '김학생 학부모', '010-1234-1234', 'parents@gmail.com', 'FEMALE', null, 1, 'ROLE_PARENTS', 0, 1, 0, NOW(), 'P'),
    ('add9fa2e-92c9-48ee-adb7-46c307ca8778', '$2a$10$nRCXAFC8IOh78cwfh9v57uJt1NzF8vLgWzn9.OOXgPx3hIk4e/2sq', '조강사', '010-1234-1234', 'otherteacher@gmail.com', 'FEMALE', null, 1, 'ROLE_TEACHER', 0, 1, 0, NOW(), 'T'),
    ('e8445639-917a-4396-8aaa-4a68dd11e4c7', '$2a$10$kdG9XoA8h0J7UirQ1xuUfuzVfa/BgGzZtEjmPc063.vrevHZfM6oK', '조학생', '010-1234-1234', 'otherstudent@gmail.com', 'MALE', null, 1, 'ROLE_STUDENT', 0, 1, 0, NOW(), 'S'),
    ('c2862de7-e8ef-4aa8-bf7d-711cd712279b', '$2a$10$S3rRiFdZMWjtaOsKeD6HxOsdq9pJqvlc6vI1wofESR1s13RUkj0PG', '조학생 학부모', '010-1234-1234', 'otherparents@gmail.com', 'FEMALE', null, 1, 'ROLE_PARENTS', 0, 1, 0, NOW(), 'P');

-- 관라자 테이블 생성 필요

-- 깅사 테이블 생성
CREATE TABLE IF NOT EXISTS teacher (
	teacher_id CHAR(36) NOT NULL,
	STATUS ENUM('ATTENDING_SCHOOL', 'GRADUATION', 'LEAVE_OF_ABSENCE'),
	UNIV VARCHAR(50),
	SUBJECT VARCHAR(30),
	PRIMARY KEY (teacher_id),
	FOREIGN KEY (teacher_id) REFERENCES member(user_id)
);

-- 강사 더미 데이터 삽입
INSERT INTO teacher
VALUES
	('98a10847-ad7e-11ef-8e5c-0242ac140002', 'GRADUATION', '한국대학교', '수학'),
        ('add9fa2e-92c9-48ee-adb7-46c307ca8778', 'GRADUATION', '행복대학교', '영어');

-- 공식 프로필 테이블 생성
CREATE TABLE IF NOT EXISTS official_profile (
	USER_ID CHAR(36) NOT NULL,
	CONTENT TEXT,
        mod_date datetime(6),
        reg_date datetime(6),
        created_by varchar(255),
        del_date datetime(6),
        deleted_by varchar(255),
        modified_by varchar(255),	
	PRIMARY KEY (USER_ID),
	FOREIGN KEY (USER_ID) REFERENCES teacher(teacher_id)
);

-- 공식 프로필 더미 데이터 삽입
INSERT INTO official_profile (USER_ID, CONTENT)
VALUES
	('98a10847-ad7e-11ef-8e5c-0242ac140002', '<p>공식 프로필 페이지를 설정해주세요.</p>'),
	('add9fa2e-92c9-48ee-adb7-46c307ca8778', '<p>공식 프로필 페이지를 설정해주세요.</p>');


CREATE TABLE IF NOT EXISTS student (

	student_id CHAR(36) NOT NULL,
	GRADE ENUM('M1', 'M2', 'M3', 'H1', 'H2', 'H3', 'N'),
	PRIMARY KEY (student_id),
	FOREIGN KEY (student_id) REFERENCES member(user_id)
);

INSERT INTO student
VALUES
('c99fd58f-b0ae-11ef-89d8-0242ac140003', 'H1'),
('e8445639-917a-4396-8aaa-4a68dd11e4c7', 'H3');

CREATE TABLE IF NOT EXISTS parents(
	user_id CHAR(36) NOT NULL,
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES member(USER_ID)
);

-- 클래스 테이블 생성
CREATE TABLE IF NOT EXISTS classroom (

	classroom_id CHAR(36) NOT NULL,
	teacher_id CHAR(36) NOT NULL,
	title VARCHAR(50) NOT NULL,
	description VARCHAR(1000),
	capacity INT NOT NULL,
	thumbnail VARCHAR(255),
	reg_date DATETIME(6) NOT NULL,
        mod_date datetime(6),
        created_by varchar(255),
        del_date datetime(6),
        deleted_by varchar(255),
        modified_by varchar(255),
	PRIMARY KEY (classroom_id),
	FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

-- 클래스 더미 데이터 삽입
INSERT INTO classroom(classroom_id, teacher_id, title, description, capacity, thumbnail, reg_date, created_by)
VALUES
	('98a12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002', '이상한수학', '수학을 잘 하고 싶은 사람들 모두 모여라', 20, 'http://어딘가', NOW(), '98a10847-ad7e-11ef-8e5c-0242ac140002'),
	('76b12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002', '이상한과학', '과학을 잘 하고 싶은 사람들 모두 모여라', 20, 'http://어딘가', NOW(), '98a10847-ad7e-11ef-8e5c-0242ac140002');


CREATE TABLE IF NOT EXISTS faq (
	faq_id BIGINT NOT NULL AUTO_INCREMENT,
	title VARCHAR(100) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	classroom_id CHAR(36) NOT NULL,
	created_by CHAR(36) NOT NULL,
	mod_date DATETIME(6),
	reg_date DATETIME(6) NOT NULL,
        del_date datetime(6),
        deleted_by varchar(255),
        modified_by varchar(255),
	PRIMARY KEY (faq_id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id),
	FOREIGN KEY (created_by) REFERENCES teacher(teacher_id)
);

INSERT INTO faq (faq_id, title, content, classroom_id, created_by, reg_date)
VALUES
	(1, '동영상 시청은 어떻게 하나요?', 'A 화면 어딘가에 박혀있는 B라는 버튼을 누르면 어디로 navigation 되는데 ~~~', '98a12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002',  NOW());

CREATE TABLE IF NOT EXISTS class_inquiry(
	inquiry_id  BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	title VARCHAR(255) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	visibility ENUM('PUBLIC', 'PRIVATE') NOT NULL,
	reg_date DATETIME(6) NOT NULL,
	mod_date DATETIME(6),
	created_by CHAR(36)  NOT NULL,
        del_date datetime(6),                                                                                   deleted_by varchar(255),                                                                                modified_by varchar(255),
	PRIMARY KEY (inquiry_id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id),
	FOREIGN KEY (created_by) REFERENCES member(user_id)
);

INSERT INTO class_inquiry (inquiry_id, classroom_id, title, content, visibility, reg_date, created_by)
VALUES
(1, '98a12345-ad7e-11ef-8e5c-0242ac140002', 
'수업 커리큘럼 내 과제는 어떻게 진행되는 건가요?', 
'안녕하세요. 이번 클래스에 참가하고 싶은 학생입니다. 수업 내에 과제 관련된 내용이 포함되어 있던데, 이것에 대한 자세한 운영 계획을 들어볼 수 있을까요?', 
'PRIVATE', 
NOW(), 
'c99fd58f-b0ae-11ef-89d8-0242ac140003');

-- INSERT INTO class_inquiry (title, content, visibility, classroom_id, created_by, reg_date)
-- VALUES
--     ('수학 강의 관련 질문', '이 강의의 내용은 언제 업데이트 되나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('수업 일정 변경', '이번 주 수업 시간이 변경되었나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('기타 문의사항', '시험과 관련된 추가 자료는 언제 제공되나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('오프라인 수업 참여', '이번 주 오프라인 수업은 언제 시작하나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('강의 자료 요청', '강의 자료를 이메일로 받을 수 있나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('학습 보조 자료 요청', '이 강의를 위한 보조 자료는 어디서 구할 수 있나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('수업 시간표 문의', '수업 시간표는 어떻게 확인하나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('과제 제출 마감일', '이번 주 과제 제출 마감일은 언제인가요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('학생과의 소통 방법', '학생들과 어떻게 소통할 수 있을까요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('시험 준비 자료 요청', '시험 준비를 위한 자료는 언제 제공되나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('보강 수업 여부', '보강 수업은 어떻게 신청하나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('강의 연기 여부', '강의가 연기될 가능성이 있나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('이벤트 안내', '이벤트에 대한 정보는 어디에서 확인할 수 있나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('학생 평가 문의', '학생 평가는 언제 이루어지나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('수업 피드백 요청', '수업에 대한 피드백은 어떻게 제출하나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('스터디 그룹 관련 질문', '스터디 그룹은 어떻게 결성되나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('학생 지원 프로그램 안내', '학생 지원 프로그램에 대해 알고 싶습니다.', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('졸업 후 진로 상담', '졸업 후 진로 상담이 가능한가요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW()),
--     ('수업에 대한 만족도 조사', '수업에 대한 만족도 조사는 언제 이루어지나요?', 'VISIBLE', '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW());

-- 수업글
CREATE TABLE IF NOT EXISTS post (
	post_id BIGINT NOT NULL AUTO_INCREMENT,
        reg_date DATETIME(6),
        mod_date DATETIME(6),                                                                                   created_by CHAR(36),
        del_date datetime(6),                                                                                   deleted_by varchar(255),                                                                                modified_by varchar(255),
	PRIMARY KEY (post_id)
);


-- 댓글 
CREATE TABLE IF NOT EXISTS reply (
	comment_id BIGINT NOT NULL AUTO_INCREMENT,
	content VARCHAR(1000) NOT NULL,
	visibility ENUM('PUBLIC', 'PRIVATE'),
	created_by CHAR(36) NOT NULL,
	reg_date DATETIME(6) NOT NULL,
        mod_date DATETIME(6),                                                                                   del_date datetime(6),                                                                                   deleted_by varchar(255),                                                                                modified_by varchar(255),
	parent_comment_id BIGINT,
	post_id BIGINT,
	class_inquiry_id BIGINT,
	PRIMARY KEY (comment_id),
	FOREIGN KEY (created_by) REFERENCES member(user_id),
	FOREIGN KEY (parent_comment_id) REFERENCES reply(comment_id),
	FOREIGN KEY (post_id) REFERENCES post(post_id),
	FOREIGN KEY (class_inquiry_id) REFERENCES class_inquiry(inquiry_id)
);

INSERT INTO reply (comment_id, content, visibility, created_by, reg_date, parent_comment_id, class_inquiry_id)
VALUES
(1, '저희 강의는 최첨단 강의어서 모두가 만족할 수 있습니다.', 'PUBLIC', '98a10847-ad7e-11ef-8e5c-0242ac140002', NOW(), NULL, 1),
(2, '그렇군요, 알겠습니다.', 'PUBLIC', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW(), 1, 1),
(3, '비밀 댓글입니다. 비밀.', 'PRIVATE', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', NOW(), NULL, 1);

CREATE TABLE IF NOT EXISTS liked (
	liked_id BIGINT NOT NULL AUTO_INCREMENT,
	created_by CHAR(36) NOT NULL,
	reg_date DATETIME(6) NOT NULL,
        mod_date DATETIME(6),                                                                                   del_date datetime(6),                                                                                   deleted_by varchar(255),                                                                                modified_by varchar(255),
	PRIMARY KEY (liked_id)
);

CREATE TABLE IF NOT EXISTS enrollment (

	enrollment_id BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	student_id CHAR(36) NOT NULL,
	created_by CHAR(36) NOT NULL,
	status ENUM('REQUEST', 'CHECKED', 'PERMITTED', 'EXPIRED'),
	cur_score VARCHAR(1000),
	target_score VARCHAR(1000),
	request VARCHAR(1000),
	determination VARCHAR(1000),
        reg_date DATETIME(6) NOT NULL,
        mod_date DATETIME(6),
        del_date DATETIME(6),
        modified_by CHAR(36),
        deleted_by CHAR(36),
	PRIMARY KEY (enrollment_id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id),
	FOREIGN KEY (student_id) REFERENCES student(student_id),
	FOREIGN KEY (created_by) REFERENCES member(user_id)
);

INSERT INTO enrollment
VALUES
(1, '98a12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'PERMITTED' ,'50점', '100 이요', '없습니다', '화이팅', NOW(), NULL, NULL, NULL, NULL),
(2, '76b12345-ad7e-11ef-8e5c-0242ac140002', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'c99fd58f-b0ae-11ef-89d8-0242ac140003', 'PERMITTED' ,'50점', '100 이요', '없습니다', '화이팅', NOW(), NULL, NULL, NULL, NULL);

CREATE TABLE IF NOT EXISTS record_lecture (

	id BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	order_num INT, 
	title VARCHAR(100) NOT NULL,
	description VARCHAR(1000) NOT NULL,
	playtime INT,
	url VARCHAR(255) NOT NULL,
	reg_date DATETIME(6) NOT NULL,
	mod_date DATETIME(6),
	del_date DATETIME(6),
	created_by CHAR(36),
	modified_by CHAR(36),
	deleted_by CHAR(36),
	PRIMARY KEY (id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id)
);


INSERT INTO record_lecture 
(id, classroom_id, created_by, del_date, deleted_by, description, mod_date, modified_by, order_num, playtime, reg_date, title, url)
VALUES
(1, '98a12345-ad7e-11ef-8e5c-0242ac140002', '98a10847-ad7e-11ef-8e5c-0242ac140002', NULL, NULL, 'test', NULL, NULL, NULL, 0, NOW(), 'test', '/videos/1038052017');

CREATE TABLE IF NOT EXISTS live_lecture (
	
	id BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	title VARCHAR(100) NOT NULL,
	start_time DATETIME(6) NOT NULL,
	end_time DATETIME(6) NOT NULL,
	study_space VARCHAR(200) NOT NULL,
	platform ENUM('ZOOM', 'GOOGLE_MEET', 'DISCORD', 'KAKAO_TALK', 'CALL', 'YOUTUBE_LIVE', 'OFFLINE') NOT NULL,
        reg_date DATETIME(6) NOT NULL,
        mod_date DATETIME(6),
        del_date DATETIME(6),
        created_by CHAR(36),
        modified_by CHAR(36),
        deleted_by CHAR(36),
	PRIMARY KEY (id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id)
	-- FOREIGN KEY (created_by) REFERENCES teacher(teacher_id)
);

CREATE TABLE IF NOT EXISTS materials (
	id BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	student_id CHAR(36),
	order_num INT,
	status ENUM('PUBLIC', 'INDIVIDUAL') NOT NULL,
	title VARCHAR(100) NOT NULL,
	description VARCHAR(2000),
	material_uri VARCHAR(1000),
        reg_date DATETIME(6) NOT NULL,
        mod_date DATETIME(6),
        del_date DATETIME(6),
        created_by CHAR(36),
        modified_by CHAR(36),
        deleted_by CHAR(36),
	PRIMARY KEY (id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id),
	FOREIGN KEY (student_id) REFERENCES student(student_id),
	FOREIGN KEY (created_by) REFERENCES member(user_id)
);

CREATE TABLE IF NOT EXISTS attendance (
	id BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	created_by CHAR(36) NOT NULL,
	reg_date TIMESTAMP NOT NULL,
	check_out_date TIMESTAMP,
	mod_date DATETIME(6),
	del_date DATETIME(6),
	modified_by CHAR(36),
	deleted_by CHAR(36),
	PRIMARY KEY (id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id),
	FOREIGN KEY (created_by) REFERENCES student(student_id)
);

CREATE TABLE IF NOT EXISTS assessment (

	id BIGINT NOT NULL AUTO_INCREMENT,
	classroom_id CHAR(36) NOT NULL,
	assessment_type ENUM('EXAM', 'ASSIGNMENT') NOT NULL,
	title VARCHAR(100) NOT NULL,
	description VARCHAR(2000),
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP NOT NULL,
	exam_text VARCHAR(2000),
	exam_uri VARCHAR(255),
	feedback_to_all TEXT,
	PRIMARY KEY (id),
	FOREIGN KEY (classroom_id) REFERENCES classroom(classroom_id)
);

CREATE TABLE IF NOT EXISTS submission(

	id BIGINT NOT NULL AUTO_INCREMENT,
	student_id CHAR(36) NOT NULL,
	assessment_id BIGINT NOT NULL,
	assessment_type ENUM('EXAM', 'ASSIGNMENT') NOT NULL,
	submission_text TEXT,
	submission_uri VARCHAR(255),
	started_at TIMESTAMP,
	ended_at TIMESTAMP,
	status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'MISSED'),
	personal_feedback TEXT,
	score INT,
	PRIMARY KEY (id),
	FOREIGN KEY (student_id) REFERENCES student(student_id),
	FOREIGN KEY (assessment_id) REFERENCES assessment(id)
);