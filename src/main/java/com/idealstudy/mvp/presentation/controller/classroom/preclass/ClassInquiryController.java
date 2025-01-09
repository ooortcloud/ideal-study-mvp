package com.idealstudy.mvp.presentation.controller.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.ClassInquiryService;
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
public class ClassInquiryController {

    @Autowired
    private final ClassInquiryService classInquiryService;

    @ForUser
    @PostMapping("/api/inquiries")
    public ResponseEntity<ClassInquiryDto> create(@RequestBody ClassInquiryDto dto){
        // ENUM 타입에 대해서도 JSON으로 string 형태로 넣어주면 자동 바인딩해줌.(단, name()값을 기준으로 함)

        return TryCatchControllerTemplate.execute(() ->
            classInquiryService.create(dto.getTitle(), dto.getContent(), dto.getClassroomId(),
                     dto.getVisibility())
        );
    }

    @GetMapping("/inquiries/classes/{classId}")
    public ResponseEntity<ClassInquiryPageResultDto> findListByClassId(@PathVariable String classId,
                                                                       @RequestParam Integer page) {

        return TryCatchControllerTemplate.execute(() -> classInquiryService.findListByClassId(classId, page));
    }

    @ForUser
    @GetMapping("/api/inquiries/users/{userId}")
    public ResponseEntity<ClassInquiryPageResultDto> findListByMemberId(@PathVariable String userId,
                                                                        @RequestParam Integer page) {

        return TryCatchControllerTemplate.execute(() -> classInquiryService.findListByMemberId(userId, page));
    }

    @GetMapping("/inquiries/{inquiryId}")
    public ResponseEntity<ClassInquiryDto> findDetail(@PathVariable Long inquiryId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        if(payload == null)
            return TryCatchControllerTemplate.execute(() -> classInquiryService.findById(inquiryId, null));

        return TryCatchControllerTemplate.execute(() -> classInquiryService.findById(inquiryId, payload.getSub()));
    }

    @ForUser
    @PutMapping("/api/inquiries/{inquiryId}")
    public ResponseEntity<ClassInquiryDto> update(@PathVariable Long inquiryId, @RequestBody ClassInquiryDto dto,
                                                  HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String userId = payload.getSub();

        return TryCatchControllerTemplate.execute(() ->
                classInquiryService.update(inquiryId, dto.getTitle(), dto.getContent(),
                dto.getClassroomId(), dto.getVisibility(), userId));
    }

    @ForUser
    @DeleteMapping("/api/inquiries/{inquiryId}")
    public ResponseEntity<?> delete(@PathVariable Long inquiryId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        return TryCatchControllerTemplate.execute(() -> {
            classInquiryService.delete(inquiryId, payload.getSub());
            return null;
        });
    }
}
