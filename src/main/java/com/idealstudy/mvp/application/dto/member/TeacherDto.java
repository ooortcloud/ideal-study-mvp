package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.SchoolRegister;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TeacherDto extends MemberDto{

    private String univ;

    private SchoolRegister status;

    private String subject;

}
