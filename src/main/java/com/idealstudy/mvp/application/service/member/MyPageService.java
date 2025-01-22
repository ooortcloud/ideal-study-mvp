package com.idealstudy.mvp.application.service.member;

import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.application.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import com.idealstudy.mvp.presentation.dto.member.MemberResponseDto;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberService와 repository는 공유하지만, 기능 자체는 분리하기 위해 다른 클래스 영역에서 개발함.
 */
@Service
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;

    private final ValidationManager validationManager;

    @Autowired
    public MyPageService(MemberRepository memberRepository, ValidationManager validationManager) {
        this.memberRepository = memberRepository;
        this.validationManager = validationManager;
    }

    public MemberResponseDto updateIntroduction(String userId, String introduction) {

        return TryCatchServiceTemplate.execute(() -> {

            MemberDto findDto = memberRepository.findById(userId);

            validationManager.validateIndividual(userId, findDto.getUserId());

            MemberDto dto = memberRepository.updateIntroduction(userId, introduction);

            return MemberMapper.INSTANCE.toResponseDto(dto);
        }, null, DBErrorMsg.UPDATE_ERROR);
    }


}
