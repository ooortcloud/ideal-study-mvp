package com.idealstudy.mvp.application.dto.member;


import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto extends MemberDto{
    StudentDto(String userId, String password, String name, String phoneAddress, String email, Gender sex,
               String referralId, Integer level, String introduction, byte[] profile, int fromSocial,
               int init, int deleted) {
        super(userId, password, name, phoneAddress, email, sex, referralId, level, Role.ROLE_STUDENT,
                introduction, profile, fromSocial, init, deleted);
    }


}
