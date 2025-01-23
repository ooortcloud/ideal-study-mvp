package com.idealstudy.mvp.presentation.dto.member;


import com.idealstudy.mvp.enums.member.Grade;
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
public class StudentRequestDto extends MemberRequestDto{


    private String school;

    private Grade grade;
}
