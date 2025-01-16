package com.idealstudy.mvp.presentation.controller.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.EnrollmentPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.EnrollmentService;
import com.idealstudy.mvp.enums.member.Role;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class EnrollmentController {

    @Autowired
    private final EnrollmentService enrollmentService;

    /// 권한 설정 문제로 엔드포인트를 분리하여 받도록 함.
    /// 각 권한 별 요청 JSON 값이 다름
    @ForStudent
    @PostMapping("/api/enrollments/student")
    public ResponseEntity<EnrollmentDto> enrollForStudent(@RequestBody EnrollmentDto dto, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto)  request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> enrollmentService.enrollForStudent(
                dto.getClassroomId(),
                studentId,
                dto.getCurScore(),
                dto.getTargetScore(),
                dto.getRequest(),
                dto.getDetermination()
        ));
    }

    @ForParents
    @PostMapping("/api/enrollments/parents")
    public ResponseEntity<EnrollmentDto> enrollForParents(@RequestBody EnrollmentDto dto, HttpServletRequest request) {

        /*
        JwtPayloadDto payload = (JwtPayloadDto)  request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

         */

        return TryCatchControllerTemplate.execute(() -> enrollmentService.enrollForParents(
                dto.getClassroomId(),
                dto.getStudentId(),
                dto.getCurScore(),
                dto.getTargetScore(),
                dto.getRequest(),
                dto.getDetermination()
        ));
    }

    /// 권한 설정 문제로 엔드포인트를 분리하여 받도록 함.
    /// 각 권한 별 반환값 양식은 같으나, 입력 파라미터가 다름
    @ForTeacher
    @GetMapping("/api/enrollments/classes/{classId}")
    public ResponseEntity<EnrollmentPageResultDto> getEnrollmentListByClassroomId(@PathVariable String classId,
                                                                                  @RequestParam int page) {

        return TryCatchControllerTemplate.execute(() ->
                enrollmentService.getListForTeacher(classId, page));
    }
    
    @ForStudent
    @GetMapping("/api/enrollments/student")
    public ResponseEntity<EnrollmentPageResultDto> getEnrollmentListByStudentId(
            @RequestParam Integer page, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> enrollmentService.getListForStudent(studentId, page));
    }

    @ForParents
    @GetMapping("/api/enrollments/parents")
    public ResponseEntity<EnrollmentPageResultDto> getEnrollmentListByParentsId(
            @RequestParam Integer page, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String parentsId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> enrollmentService.getListForParents(parentsId, page));
    }

    /// 각 역할군에 따라 어플리케이션 검증 로직이 달라질 수 있으므로 컨트롤러를 전부 달리해야 하는 것이 이상적
    /// 하지만 GET의 경우에는 입력 파라미터와 반환값 양식이 모두 동일하다고 판단하여 WAS 내에서 분기 처리로 구현해봄.
    @ForUser
    @GetMapping("/api/enrollments/{enrollmentId}")
    public ResponseEntity<EnrollmentDto> getInfo(@PathVariable Long enrollmentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();
        Role role = payload.getRole();

        if(role == Role.ROLE_TEACHER)
            return TryCatchControllerTemplate.execute(() -> enrollmentService.getInfoForTeacher(enrollmentId, userId));

        if(role == Role.ROLE_STUDENT)
            return TryCatchControllerTemplate.execute(() -> enrollmentService.getInfoForStudent(enrollmentId, userId));

        if(role == Role.ROLE_PARENTS)
            return TryCatchControllerTemplate.execute(() -> enrollmentService.getInfoForParents(enrollmentId, userId));

        /// admin 역할군의 절대성을 위해 풀어줄 수도 있기는 함. 하지만 일단은 설계하지 않았으니 배제하는 식으로 구현함.
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    @ForStudent
    @PatchMapping("/api/enrollments/student/{enrollmentId}")
    public ResponseEntity<Object> updateForStudent(@PathVariable Long enrollmentId, HttpServletRequest request,
                                                   @RequestBody EnrollmentDto dto) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String studentId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> enrollmentService.update(enrollmentId, studentId,
                dto.getCurScore(), dto.getTargetScore(), dto.getRequest(), dto.getDetermination()));
    }

    @ForParents
    @PatchMapping("/api/enrollments/parents/{enrollmentId}")
    public ResponseEntity<Object> updateForParents(@PathVariable Long enrollmentId, HttpServletRequest request,
                                                   @RequestBody EnrollmentDto dto) {
        
        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String parentsId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> enrollmentService.update(enrollmentId, parentsId,
                dto.getCurScore(), dto.getTargetScore(), dto.getRequest(), dto.getDetermination()));
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
    public ResponseEntity<Object> reject(@PathVariable Long enrollmentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            enrollmentService.reject(enrollmentId, teacherId);
            return null;
        });
    }

    @ForTeacher
    @PatchMapping("/api/enrollments/{enrollmentId}/accept")
    public ResponseEntity<Object> accept(@PathVariable Long enrollmentId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            enrollmentService.accept(enrollmentId, teacherId);
            return null;
        });
    }
}
