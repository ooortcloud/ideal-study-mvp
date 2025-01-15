package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.repository.OfficialProfileRepository;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.integration.infrastructure.helper.InfraDummyMemberGenerator;
import com.idealstudy.mvp.util.RandomValueGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OfficialProfileRepositoryTest {

    private final OfficialProfileRepository officialProfileRepository;

    private final InfraDummyMemberGenerator dummyMemberGenerator;

    private final RandomValueGenerator randomValueGenerator;

    @Autowired
    public OfficialProfileRepositoryTest(OfficialProfileRepository repository,
                                         InfraDummyMemberGenerator dummyMemberGenerator, RandomValueGenerator randomValueGenerator) {

        this.officialProfileRepository = repository;
        this.dummyMemberGenerator = dummyMemberGenerator;
        this.randomValueGenerator = randomValueGenerator;
    }

    @Test
    public void createAndSearchAndUpdate() {

        String userId = randomValueGenerator.createRandomValue();

        dummyMemberGenerator.setToken(userId, Role.ROLE_TEACHER);

        /// create
        TeacherDto dummyDto = dummyMemberGenerator.createDummyTeacher(userId);

        String teacherId = dummyDto.getUserId();
        String initContent = getContent(false);
        OfficialProfileDto dto = officialProfileRepository.create(teacherId, initContent);
        Assertions.assertThat(dto.getTeacherId()).isEqualTo(teacherId);
        Assertions.assertThat(dto.getContent()).isEqualTo(initContent);

        /// search
        OfficialProfileDto findDto = officialProfileRepository.findByTeacherId(teacherId);
        Assertions.assertThat(findDto.getTeacherId()).isEqualTo(teacherId);
        Assertions.assertThat(findDto.getContent()).isEqualTo(initContent);

        /// update
        String updateContent = getContent(true);
        OfficialProfileDto updateDto = officialProfileRepository.update(teacherId, updateContent);
        Assertions.assertThat(updateDto.getTeacherId()).isEqualTo(teacherId);
        Assertions.assertThat(updateDto.getContent()).isEqualTo(updateContent);
    }

    private String getContent(boolean update) {

        return "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>official profile 예시</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>안녕하세요!</h1>\n" +
                "    <p>official profile 예시입니다.</p>\n" +
                (update ? "<p>업데이트된 내용입니다.</p>\n" : "") +
                "</body>\n" +
                "</html>";
    }
}
