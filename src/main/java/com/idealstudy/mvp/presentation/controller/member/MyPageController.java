package com.idealstudy.mvp.presentation.controller.member;

import com.idealstudy.mvp.application.service.member.MyPageService;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage/")
public class MyPageController {

    private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    /*
        현재 selectMyPage()와 selectIntroduction() 기능의 경계가 모호한 상태.(API 정의서에 그 이유를 자세히 작성해둠)
     */
    @GetMapping("{userId}")
    public void selectMyPage(@PathVariable String userId) {

    }

    @GetMapping("{userId}/bio")
    public void selectIntroduction(@PathVariable String userId) {

    }

    @PutMapping(value = "/{userId}/bio", consumes = "text/plain")
    public ResponseEntity<MemberResponseDto> updateIntroduction(@PathVariable String userId,
                                                                @RequestBody String introduction) {
        return TryCatchControllerTemplate.execute(() -> myPageService.updateIntroduction(userId, introduction));
    }
}
