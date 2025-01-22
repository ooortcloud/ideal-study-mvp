package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.service.member.MemberService;
import com.idealstudy.mvp.security.dto.JwtPayloadDto;
import com.idealstudy.mvp.helper.JwtHelper;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommonController {

    @Autowired
    private final JwtHelper jwtUtil;

    @Autowired
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity<JwtPayloadDto> main(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JwtPayloadDto payload = (JwtPayloadDto) request.getAttribute("jwtPayload");

        return new ResponseEntity<>(payload, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/api/check-first-login")
    public ResponseEntity<String> checkFirst(HttpServletRequest request) {

        JwtPayloadDto paylaod = (JwtPayloadDto) request.getAttribute("jwtPayload");
        String tokenId = paylaod.getSub();

        return TryCatchControllerTemplate.execute(() -> {
            boolean isFirst = memberService.isFirst(tokenId);

            if(isFirst)
                return "first";

            else
                return "complete";
        });
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<String> returnFavicon() {
        // 204 NO CONTENT
        return new ResponseEntity<>("현재 favicon은 존재하지 않음", HttpStatusCode.valueOf(204));
    }
}
