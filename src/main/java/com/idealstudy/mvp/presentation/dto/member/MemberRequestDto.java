package com.idealstudy.mvp.presentation.dto.member;

import com.idealstudy.mvp.enums.member.Gender;
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
public class MemberRequestDto {

    private String name;

    private String phoneAddress; //

    private Gender sex;
}
