package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.integration.infrastructure.helper.InfraDummyClassGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@SpringBootTest
@Transactional
public class LikedClassroomRepositoryTest {

    private LikedRepository likedRepository;

    private InfraDummyClassGenerator dummyClassGenerator;

    @Autowired
    public LikedClassroomRepositoryTest(@Qualifier("likedClassroomRepositoryImpl") LikedRepository likedRepository,
                                        InfraDummyClassGenerator dummyClassGenerator) {
        this.likedRepository = likedRepository;
        this.dummyClassGenerator = dummyClassGenerator;
    }

    @Test
    public void createAndCountAndDeleteLike() throws Exception {

        Map<String, Object> tempMap = dummyClassGenerator.createDummy();
        ClassroomResponseDto dummyClassroom = (ClassroomResponseDto) tempMap.get("classroomResponseDto");

        long likedId = likedRepository.create(dummyClassroom.getId());
        Assertions.assertThat(likedRepository.countById(dummyClassroom.getId())).isOne();

        likedRepository.delete(likedId);

        Assertions.assertThatThrownBy(() -> likedRepository.getCreatedBy(likedId));
    }
}
