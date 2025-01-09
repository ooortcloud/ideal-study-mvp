-- idealstudy 데이터베이스 생성!!
CREATE DATABASE IF NOT EXISTS idealstudy;

-- 관리자 계정 생성
CREATE USER IF NOT EXISTS 'manager'@'%' IDENTIFIED BY 'manager';

-- 관리자 권한 부여
GRANT ALL PRIVILEGES ON idealstudy.* TO 'manager'@'%';

-- 권한 변경 사항 적용
FLUSH PRIVILEGES;