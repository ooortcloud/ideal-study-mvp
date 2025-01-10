package com.idealstudy.mvp.unit.service;

import com.idealstudy.mvp.unit.util.TestServiceUtil;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import com.idealstudy.mvp.application.service.member.MyPageService;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MyPageServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ValidationManager validationManager;

    @InjectMocks
    private MyPageService myPageService;

    @Test
    public void updateIntroduction() {

        MemberDto dummyDto = TestServiceUtil.createDummyMemberDto();

        Mockito.when(memberRepository.findById(dummyDto.getUserId()))
                .thenReturn(dummyDto);

        String newIntroduction = "helloWorld!";
        MemberDto changedDto = MemberDto.builder()
                .userId(dummyDto.getUserId())
                .email(dummyDto.getEmail())
                .role(dummyDto.getRole())
                .name(dummyDto.getName())
                .phoneAddress(dummyDto.getPhoneAddress())
                .introduction(newIntroduction)
                .build();

        Mockito.when(memberRepository.updateIntroduction(dummyDto.getUserId(), newIntroduction))
                .thenReturn(changedDto);

        String userId = dummyDto.getUserId();

        MemberResponseDto responseDto = myPageService.updateIntroduction(userId, newIntroduction);
        Assertions.assertThat(dummyDto.getEmail()).isEqualTo(changedDto.getEmail());
        Assertions.assertThat(dummyDto.getRole()).isEqualTo(changedDto.getRole());
        Assertions.assertThat(dummyDto.getIntroduction()).isNotEqualTo(changedDto.getIntroduction());
        Assertions.assertThat(changedDto.getIntroduction()).isEqualTo(newIntroduction);
    }


}
