package com.idealstudy.mvp.infrastructure.impl.member;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.enums.member.MemberError;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.MemberJpaRepository;
import com.idealstudy.mvp.mapstruct.MemberMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

@Repository
@Log4j2
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void create(MemberDto dto) {

        dto.setFirst(true);
        MemberEntity entity = memberMapper.dtoToEntity(dto);

        memberJpaRepository.save(entity);
    }

    @Override
    public MemberDto findById(String id) {

        Optional<MemberEntity> result = memberJpaRepository.findById(id);

        if(result.isEmpty())
            throw new NullPointerException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result.get());
    }

    @Override
    public MemberDto findByEmail(String email) {

        MemberEntity result = memberJpaRepository.findByEmail(email);

        if(result == null)
            throw new NullPointerException(MemberError.NOT_REGISTERED_MEMBER.getMsg());

        return memberMapper.entityToDto(result);
    }

    @Override
    public MemberPageResultDto findMembers(PageRequestDto requestDto) {

        Pageable pageable = requestDto.getPageable(Sort.by("regDate").ascending());

        Page<MemberEntity> result = memberJpaRepository.findAll(pageable);

        Function<MemberEntity, MemberDto> fn = (entity -> memberMapper.entityToDto(entity));

        return memberMapper.toApplicationPageResult(new PageResultDto<>(result, fn));
    }

    @Override
    public MemberDto update(MemberDto dto) {

        MemberEntity entity = memberJpaRepository.findById(dto.getUserId()).orElseThrow();
        memberMapper.updateEntityFromDto(dto, entity);

        MemberEntity result = memberJpaRepository.save(entity);

        return memberMapper.entityToDto(result);
    }

    @Override
    public boolean deleteById(String id) {

        MemberDto dto = null;
        try {
            dto = memberMapper.entityToDto(memberJpaRepository.findById(id).orElseThrow());
            dto.setDeleted(true);
            memberJpaRepository.save(memberMapper.dtoToEntity(dto));
        } catch (Exception e) {
            log.info(e + " : " + e.getMessage());
            return false;
        }

        return true;
    }

}
