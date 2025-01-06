package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OfficialProfileController {

    @Autowired
    private final OfficialProfileService officialProfileService;

    @GetMapping("/officialProfiles/{userId}")
    public ResponseEntity<OfficialProfileDto> selectOne(@PathVariable String userId) {

        return TryCatchControllerTemplate.execute(() -> officialProfileService.selectOne(userId));
    }

    @ForTeacher
    @PutMapping(path= "/api/officialProfiles", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<OfficialProfileDto> update(@RequestBody String html, HttpServletRequest request) {

        JwtPayloadDto token = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String teacherId = token.getSub();

        return TryCatchControllerTemplate.execute(() -> officialProfileService.update(teacherId, html));
    }
}
