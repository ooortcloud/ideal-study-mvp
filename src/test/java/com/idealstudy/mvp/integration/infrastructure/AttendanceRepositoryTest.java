package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.TestRepositoryUtil;
import com.idealstudy.mvp.application.repository.inclass.AttendanceRepository;
import com.idealstudy.mvp.application.repository.preclass.ClassInquiryRepository;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AttendanceRepositoryTest {

    private final AttendanceRepository attendanceRepository;

    // 테스트 이외에 의존성 없음
    private final TestRepositoryUtil testRepositoryUtil;
    private Long autoIncrement;

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    private static final Long EXIST_ID = 1L;

    private static final String TABLE_NAME = "attendance";
    
    // id 정보를 제공하기 위함
    private final HttpServletRequest request;

    @Autowired
    public AttendanceRepositoryTest(AttendanceRepository attendanceRepository, TestRepositoryUtil testRepositoryUtil, 
                                    HttpServletRequest request) {
        this.attendanceRepository = attendanceRepository;
        this.testRepositoryUtil = testRepositoryUtil;

        this.request = request;
    }

    @BeforeEach
    public void getAutoIncrement() {
        autoIncrement = testRepositoryUtil.getAutoIncrement(TABLE_NAME);
    }


    @Test
    public void testCheckIn() {

        setId(STUDENT_ID);

        
    }

    @Test
    public void teatCheckOut() {

    }

    @Test
    public void testGetIndividualAttendance() {

    }

    @Test
    public void testGetTodayClassroomAttendance() {

    }

    private void setId(String userId) {

        JwtPayloadDto dto = JwtPayloadDto.builder()
                .sub(userId)
                .build();

        request.setAttribute("jwtPayload", dto);
    }
}
