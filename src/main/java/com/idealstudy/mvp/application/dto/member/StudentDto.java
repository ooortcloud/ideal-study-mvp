package com.idealstudy.mvp.application.dto.member;

import com.idealstudy.mvp.enums.member.Grade;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class StudentDto extends MemberDto{

    private String school;

    private Grade grade;

}
