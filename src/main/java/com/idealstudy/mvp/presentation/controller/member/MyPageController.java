package com.idealstudy.mvp.presentation.controller.member;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage/")
public class MyPageController {

    @GetMapping("{userId}")
    public void selectMyPage(@PathVariable String userId) {

    }

    @GetMapping("{userId}/bio")
    public void selectIntroduction(@PathVariable String userId) {

    }

    @PutMapping("/{userId}/bio")
    public void updateIntroduction(@PathVariable String userId) {

    }
}
