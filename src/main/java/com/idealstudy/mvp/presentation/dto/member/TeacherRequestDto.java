package com.idealstudy.mvp.presentation.dto.member;

import com.idealstudy.mvp.enums.member.SchoolRegister;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDto extends MemberRequestDto{

    private String univ;

    private SchoolRegister status;

    private String subject;
}
