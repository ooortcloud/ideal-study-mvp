package com.idealstudy.mvp.application.service.member;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.EmailRepository;
import com.idealstudy.mvp.infrastructure.EmailSender;
import com.idealstudy.mvp.infrastructure.dto.SignUpDto;
import com.idealstudy.mvp.util.RandomValueGenerator;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final EmailRepository emailRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final EmailSender emailSender;


    public SignUpDto sendSignUpEmail(String userEmail, Role role) throws Exception {

        String token = RandomValueGenerator.createRandomValue().split("-")[0];
        SignUpDto dto = emailRepository.addToken(token, userEmail, role);

        emailSender.sendEmail(userEmail, token);

        return dto;
    }

    public Boolean isEmailDuplication(String token) {

        try {
            SignUpDto savedToken = emailRepository.getToken(token);

            MemberDto dto = memberRepository.findByEmail(savedToken.getEmail());
            if(dto != null)
                return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

        return null;
    }


}
