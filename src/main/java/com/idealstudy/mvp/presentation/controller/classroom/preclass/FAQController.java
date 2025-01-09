package com.idealstudy.mvp.presentation.controller.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.application.service.classroom.preclass.FAQService;
import com.idealstudy.mvp.presentation.dto.classroom.FAQRequestDto;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FAQController {

    private final FAQService faqService;

    @ForTeacher
    @PostMapping("/api/faqs/{classroomId}")
    public ResponseEntity<String> create(HttpServletRequest request, @PathVariable String classroomId,
                                 @RequestBody FAQRequestDto requestDto) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {

                faqService.create(teacherId, classroomId, requestDto.getTitle(), requestDto.getContent());
                return null;
        }
        );
    }

    @GetMapping("/faqs/classes/{classId}")
    public ResponseEntity<FAQPageResultDto> searchList(@PathVariable String classId, @RequestParam Integer page) {

        return TryCatchControllerTemplate.execute(() -> faqService.findList(page, classId));
    }

    @GetMapping("/faqs/{faqId}")
    public ResponseEntity<FAQDto> searchDetail(@PathVariable Long faqId) {

        return TryCatchControllerTemplate.execute(() -> faqService.findById(faqId));
    }

    @ForTeacher
    @PutMapping("/api/faqs/{faqId}")
    public ResponseEntity<FAQDto> update(@PathVariable Long faqId, @RequestBody FAQRequestDto dto,
                                         HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() ->
                faqService.update(faqId, dto.getTitle(), dto.getContent(), teacherId));
    }

    @ForTeacher
    @DeleteMapping("/api/faqs/{faqId}")
    public ResponseEntity<Object> delete(@PathVariable Long faqId, HttpServletRequest request) {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = payload.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            faqService.delete(faqId, teacherId);
            return null;
        }
        );
    }
}
