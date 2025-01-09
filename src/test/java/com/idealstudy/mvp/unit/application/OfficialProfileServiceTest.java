package com.idealstudy.mvp.unit.application;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.application.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class OfficialProfileServiceTest {

    private final OfficialProfileService officialProfileService;

    // 더미 데이터 생성용. 이외에 의존성 없음.
    private final MemberRepository memberRepository;

    private static final String TEACHER_ID = UUID.randomUUID().toString();

    @Autowired
    public OfficialProfileServiceTest(OfficialProfileService officialProfileService, MemberRepository memberRepository) {
        this.officialProfileService = officialProfileService;
        this.memberRepository = memberRepository;
    }

    @BeforeEach
    public void createDummyTeacher() {

        memberRepository.createDummyTeacher(TEACHER_ID);
    }

    @Test
    public void saveAndFindOne() {

        officialProfileService.create(TEACHER_ID);

        OfficialProfileDto dto = officialProfileService.selectOne(TEACHER_ID);

        Assertions.assertThat(dto.getTeacherId()).isEqualTo(TEACHER_ID);
    }

    @Test
    public void update() {

        officialProfileService.create(TEACHER_ID);

        String html = "<h1>아무개 공식 프로필</h1>\n" +
                "<p>저는 수학 정말 잘합니다. 믿어주세요.</p>";
        String teacherId = TEACHER_ID;
        OfficialProfileDto resultDto = officialProfileService.update(teacherId, html);

        Assertions.assertThat(resultDto).isNotNull();
        Assertions.assertThat(resultDto.getTeacherId()).isEqualTo(TEACHER_ID);
        Assertions.assertThat(resultDto.getContent()).isEqualTo(html);
    }
}
