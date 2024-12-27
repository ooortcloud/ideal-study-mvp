package com.idealstudy.mvp.presentation.controller.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.EnrollmentService;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EnrollmentController {

    @Autowired
    private final EnrollmentService enrollmentService;

    @ForUser
    @PostMapping("/api/enrollments")
    public ResponseEntity<EnrollmentDto> enroll(@RequestBody EnrollmentDto dto) {

        return TryCatchControllerTemplate.execute(() -> enrollmentService.enroll(
                dto.getClassroomId(),
                dto.getCreatedBy(),
                dto.getStudentId(),
                dto.getCurScore(),
                dto.getTargetScore(),
                dto.getRequest(),
                dto.getDetermination()
        ));
    }

    @ForTeacher
    @GetMapping("/api/enrollments/classes/{classId}")
    public ResponseEntity<EnrollmentPageResultDto> getEnrollmentListByClassroomId(@PathVariable String classId,
                                                                                  @RequestParam int page) {

        return TryCatchControllerTemplate.execute(() ->
                enrollmentService.getList(classId, page));
    }

    @ForUser
    @GetMapping("/api/enrollments/users/{userId}")
    public void getEnrollmentListByUserId(@PathVariable String userId) {

        // 어떤 STATUS를 기준으로 검색할 것인가?
    }

    @ForUser
    @GetMapping("/api/enrollments/{enrollmentId}")
    public ResponseEntity<EnrollmentDto> getInfo(@PathVariable Long enrollmentId) {

        return TryCatchControllerTemplate.execute(() -> enrollmentService.getInfo(enrollmentId));
    }

    public void update() {

    }

    @ForUser
    @DeleteMapping("/api/enrollments/{enrollmentId}")
    public ResponseEntity<Object> drop(@PathVariable Long enrollmentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        String applicantId = payload.getSub();
        return TryCatchControllerTemplate.execute(() -> {
            enrollmentService.drop(enrollmentId, applicantId);
            return null;
        });

    }
}
