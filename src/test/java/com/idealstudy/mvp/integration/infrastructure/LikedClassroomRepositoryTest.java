package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.integration.infrastructure.helper.DummyClassGenerator;
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

    private DummyClassGenerator dummyClassGenerator;

    @Autowired
    public LikedClassroomRepositoryTest(@Qualifier("likedClassroomRepositoryImpl") LikedRepository likedRepository,
                                        DummyClassGenerator dummyClassGenerator) {
        this.likedRepository = likedRepository;
        this.dummyClassGenerator = dummyClassGenerator;
    }

    @Test
    public void createAndDelete() throws Exception {

        Map<String, Object> tempMap = dummyClassGenerator.createDummy();
        ClassroomResponseDto classroomResponseDto = (ClassroomResponseDto) tempMap.get("classroomResponseDto");

        int count = likedRepository.create(classroomResponseDto.getId());
        Assertions.assertThat(count).isOne();

        // likedRepository.delete
    }

    @Test
    public void countById() {

    }
}
