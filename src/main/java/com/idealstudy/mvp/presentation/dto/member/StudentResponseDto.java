package com.idealstudy.mvp.presentation.dto.member;

import com.idealstudy.mvp.enums.member.Grade;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class StudentResponseDto extends MemberResponseDto{

    private String school;

    private Grade grade;
}
