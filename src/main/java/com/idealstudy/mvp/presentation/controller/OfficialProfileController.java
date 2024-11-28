package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.security.annotation.ForTeacher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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

        OfficialProfileDto returnDto = null;
        try {
            returnDto = officialProfileService.selectOne(userId);
        } catch (Exception e) {
            log.error("존재하지 않는 강사 공식 페이지");
            return new ResponseEntity<OfficialProfileDto>(HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<OfficialProfileDto>(returnDto, HttpStatusCode.valueOf(200));
    }

    @ForTeacher
    @PutMapping("/{userId}")
    public ResponseEntity<OfficialProfileDto> update(@PathVariable String userId, @RequestBody OfficialProfileDto dto) {
        // 어떤 값을 출력해야 하는가?

        OfficialProfileDto returnDto = null;
        try {
            returnDto = officialProfileService.update(userId);
        } catch (Exception e) {
            log.error("존재하지 않는 강사 공식 페이지");
            return new ResponseEntity<OfficialProfileDto>(HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<OfficialProfileDto>(returnDto, HttpStatusCode.valueOf(200));
    }
}
