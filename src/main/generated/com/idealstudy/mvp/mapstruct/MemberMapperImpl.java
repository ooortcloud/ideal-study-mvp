package com.idealstudy.mvp.mapstruct;

import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.MemberEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-28T20:02:59+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto entityToDto(MemberEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MemberDto.MemberDtoBuilder memberDto = MemberDto.builder();

        memberDto.userId( entity.getUserId() );
        memberDto.password( entity.getPassword() );
        memberDto.name( entity.getName() );
        memberDto.phoneAddress( entity.getPhoneAddress() );
        memberDto.email( entity.getEmail() );
        memberDto.sex( entity.getSex() );
        memberDto.referralId( entity.getReferralId() );
        memberDto.level( entity.getLevel() );
        memberDto.role( entity.getRole() );
        memberDto.introduction( entity.getIntroduction() );
        byte[] profile = entity.getProfile();
        if ( profile != null ) {
            memberDto.profile( Arrays.copyOf( profile, profile.length ) );
        }
        memberDto.fromSocial( entity.getFromSocial() );
        memberDto.init( entity.getInit() );
        memberDto.deleted( entity.getDeleted() );

        return memberDto.build();
    }

    @Override
    public MemberEntity dtoToEntity(MemberDto dto) {
        if ( dto == null ) {
            return null;
        }

        MemberEntity.MemberEntityBuilder memberEntity = MemberEntity.builder();

        memberEntity.userId( dto.getUserId() );
        memberEntity.password( dto.getPassword() );
        memberEntity.name( dto.getName() );
        memberEntity.phoneAddress( dto.getPhoneAddress() );
        memberEntity.email( dto.getEmail() );
        memberEntity.sex( dto.getSex() );
        memberEntity.referralId( dto.getReferralId() );
        memberEntity.level( dto.getLevel() );
        memberEntity.role( dto.getRole() );
        memberEntity.introduction( dto.getIntroduction() );
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            memberEntity.profile( Arrays.copyOf( profile, profile.length ) );
        }
        memberEntity.fromSocial( dto.getFromSocial() );
        memberEntity.init( dto.getInit() );
        memberEntity.deleted( dto.getDeleted() );

        return memberEntity.build();
    }

    @Override
    public void updateEntityFromDto(MemberDto dto, MemberEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getPhoneAddress() != null ) {
            entity.setPhoneAddress( dto.getPhoneAddress() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getSex() != null ) {
            entity.setSex( dto.getSex() );
        }
        if ( dto.getReferralId() != null ) {
            entity.setReferralId( dto.getReferralId() );
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getIntroduction() != null ) {
            entity.setIntroduction( dto.getIntroduction() );
        }
        byte[] profile = dto.getProfile();
        if ( profile != null ) {
            entity.setProfile( Arrays.copyOf( profile, profile.length ) );
        }
        entity.setFromSocial( dto.getFromSocial() );
        entity.setInit( dto.getInit() );
        entity.setDeleted( dto.getDeleted() );
    }

    @Override
    public MemberPageResultDto toApplicationPageResult(PageResultDto<MemberDto, MemberEntity> pageResultDto) {
        if ( pageResultDto == null ) {
            return null;
        }

        MemberPageResultDto memberPageResultDto = new MemberPageResultDto();

        List<MemberDto> list = pageResultDto.getDtoList();
        if ( list != null ) {
            memberPageResultDto.setDtoList( new ArrayList<MemberDto>( list ) );
        }
        memberPageResultDto.setTotalPage( pageResultDto.getTotalPage() );
        memberPageResultDto.setPage( pageResultDto.getPage() );
        memberPageResultDto.setSize( pageResultDto.getSize() );
        memberPageResultDto.setStartPage( pageResultDto.getStartPage() );
        memberPageResultDto.setEndPage( pageResultDto.getEndPage() );
        memberPageResultDto.setHasPrev( pageResultDto.isHasPrev() );
        memberPageResultDto.setHasNext( pageResultDto.isHasNext() );
        List<Integer> list1 = pageResultDto.getPageList();
        if ( list1 != null ) {
            memberPageResultDto.setPageList( new ArrayList<Integer>( list1 ) );
        }

        return memberPageResultDto;
    }
}
