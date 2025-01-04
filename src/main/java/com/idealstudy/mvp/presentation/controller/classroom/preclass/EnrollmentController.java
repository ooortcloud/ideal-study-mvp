package com.idealstudy.mvp.presentation.controller.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.EnrollmentService;
import com.idealstudy.mvp.security.annotation.ForParents;
import com.idealstudy.mvp.security.annotation.ForStudent;
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
    public ResponseEntity<EnrollmentDto> enroll(@RequestBody EnrollmentDto dto, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto)  request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> enrollmentService.enroll(
                dto.getClassroomId(),
                dto.getCreatedBy(),
                studentId,
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

    @ForStudent
    @GetMapping("/api/enrollments/users")
    public void getEnrollmentListByUserId(HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();


    }

    @ForParents
    @GetMapping("/api/enrollments/users/{studentId}")
    public void getEnrollmentListByUserId(@PathVariable String studentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String parentsId = payload.getSub();



    }

    @ForUser
    @GetMapping("/api/enrollments/{enrollmentId}")
    public ResponseEntity<EnrollmentDto> getInfo(@PathVariable Long enrollmentId) {

        return TryCatchControllerTemplate.execute(() -> enrollmentService.getInfo(enrollmentId));
    }

    @ForStudent
    @PatchMapping("/api/enrollments/student/{enrollmentId}")
    public void updateForStudent() {

    }

    @ForParents
    @PatchMapping("/api/enrollments/parents/{enrollmentId}")
    public void updateForParents() {

        //enrollmentId를 사용하여 studentId를 추적할 수 있다.
    }

    @ForStudent
    @DeleteMapping("/api/enrollments/student/{enrollmentId}")
    public ResponseEntity<Object> dropForStudent(@PathVariable Long enrollmentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        String applicantId = payload.getSub();
        return TryCatchControllerTemplate.execute(() -> {
            enrollmentService.drop(enrollmentId, applicantId);
            return null;
        });
    }

    @ForParents
    @DeleteMapping("/api/enrollments/parents/{enrollmentId}")
    public ResponseEntity<Object> dropForParents(@PathVariable Long enrollmentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        String applicantId = payload.getSub();
        return TryCatchControllerTemplate.execute(() -> {
            enrollmentService.drop(enrollmentId, applicantId);
            return null;
        });
    }

    @ForTeacher
    @PatchMapping("/api/enrollments/{enrollmentId}/reject")
    public void reject() {

    }

    @ForTeacher
    @PatchMapping("/api/enrollments/{enrollmentId}/accept")
    public void accept() {

    }
}
