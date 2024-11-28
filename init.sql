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
    sex ENUM('MALE', 'FEMALE') NOT NULL,
    referral_id CHAR(36),
    level INT DEFAULT 0,
    role ENUM('ROLE_GUEST', 'ROLE_STUDENT', 'ROLE_TEACHER', 'ROLE_PARENTS', 'ROLE_ADMIN') NOT NULL,  -- 수정된 부분
    from_social TINYINT DEFAULT 0,
    init TINYINT DEFAULT 1,
    deleted TINYINT DEFAULT 0,
    mod_date datetime(6),
    reg_date datetime(6),
    dtype varchar(31) NOT NULL,
    created_by varchar(255),
    del_date datetime(6),
    deleted_by varchar(255),
    modified_by varchar(255),
    introduction varchar(255) default '안녕하세요. 잘 부탁드립니다.',
    image_data longblob
);

-- 멤버 데이터 삽입
INSERT INTO member (user_id, password, name, phone_address, email, sex, referral_id, level, role, from_social, init, deleted, reg_date, dtype)
VALUES
    (UUID(), '$2a$10$J03L0oKiIDC5mhdYxzOTresMpd9gMfqbJakKr.iHAsgUbq/To2VZO', '관리자', '010-1234-1234', 'admin@gmail.com', 'MALE', null, 1, 'ROLE_ADMIN', 0, 1, 0, NOW(), 'A'),
    (UUID(), '$2a$10$/essrJvIRI8sf52JKG/YHO.5bv6JOqmk8UFD.kFK8a12WtQZ.t2sK', '학생', '010-1234-1234', 'student@gmail.com', 'MALE', null, 1, 'ROLE_STUDENT', 0, 1, 0, NOW(), 'S'),
    (UUID(), '$2a$10$J03L0oKiIDC5mhdYxzOTresMpd9gMfqbJakKr.iHAsgUbq/To2VZO', '강사', '010-1234-1234', 'teacher@gmail.com', 'MALE', null, 1, 'ROLE_TEACHER', 0, 1, 0, NOW(), 'T'),
    (UUID(), '$2a$10$tPBgYrrCy8kKo7G.w5uVJeCcGPaCDOC80oV5qK2PE68THsjk443wy', '학부모', '010-1234-1234', 'parents@gmail.com', 'FEMALE', null, 1, 'ROLE_PARENTS', 0, 1, 0, NOW(), 'P');