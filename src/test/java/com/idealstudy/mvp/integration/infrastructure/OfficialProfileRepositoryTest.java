package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.application.repository.MemberRepository;
import com.idealstudy.mvp.application.repository.OfficialProfileRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class OfficialProfileRepositoryTest {

    private final MemberRepository memberRepository;
    private final OfficialProfileRepository officialProfileRepository;

    private static final String USER_ID = UUID.randomUUID().toString();

    private static final String TEACHER_ID = "98a10847-ad7e-11ef-8e5c-0242ac140002";

    private static final String CLASSROOM_ID = "98a12345-ad7e-11ef-8e5c-0242ac140002";

    private static final String STUDENT_ID = "c99fd58f-b0ae-11ef-89d8-0242ac140003";

    private static final String OTHER_STUDENT_ID = "e8445639-917a-4396-8aaa-4a68dd11e4c7";

    private static final String PARENTS_ID = "c99fd83e-b0ae-11ef-89d8-0242ac140003";

    @Autowired
    public OfficialProfileRepositoryTest(MemberRepository memberRepository, OfficialProfileRepository repository) {
        this.memberRepository = memberRepository;
        this.officialProfileRepository = repository;
    }

    @BeforeEach
    public void createDummyTeacher() {

        String password = UUID.randomUUID().toString();
        String email = "testteacher@gmail.com";
        Integer fromSocial = 0;

        memberRepository.createTeacher(password, email, fromSocial);

        // 추가 정보 수정까지 해야 함
        String univ = "한국대학교";
        SchoolRegister schoolRegister = SchoolRegister.GRADUATION;
        String subject = "수학";
    }

    @Test
    public void save() {

        MemberDto memberDto = memberRepository.findById(USER_ID);
        Assertions.assertThat(memberDto).isNotNull();
        Assertions.assertThat(memberDto.getUserId()).isEqualTo(USER_ID);

        String univ = "한국대학교";
        TeacherDto teacherDto = memberRepository.findTeacherById(USER_ID);
        Assertions.assertThat(teacherDto).isNotNull();
        Assertions.assertThat(teacherDto.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(teacherDto.getUniv()).isEqualTo(univ);

        String initMsg = "<p>최초 프로필 생성됨</p>";

        officialProfileRepository.create(USER_ID);

        OfficialProfileDto dto = officialProfileRepository.findByTeacherId(USER_ID);
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getContent()).isEqualTo(initMsg);
    }

    @Test
    public void update() {

        officialProfileRepository.create(USER_ID);

        String html = "<h1>아무개 공식 프로필</h1>\n" +
                "<p>저는 수학 정말 잘합니다. 믿어주세요.</p>";

        String teacherId = TEACHER_ID;

        officialProfileRepository.update(teacherId, html);

        OfficialProfileDto resultDto = officialProfileRepository.findByTeacherId(USER_ID);

        Assertions.assertThat(resultDto).isNotNull();
        Assertions.assertThat(resultDto.getTeacherId()).isEqualTo(USER_ID);
        Assertions.assertThat(resultDto.getContent().isEmpty()).isFalse();
        Assertions.assertThat(resultDto.getContent()).isEqualTo(html);
    }
}
