package com.idealstudy.mvp.integration.infrastructure;

import com.idealstudy.mvp.application.repository.LikedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class LikedClassroomRepositoryTest {

    private LikedRepository likedRepository;

    @Autowired
    public LikedClassroomRepositoryTest(@Qualifier("likedClassroomRepositoryImpl") LikedRepository likedRepository) {
        this.likedRepository = likedRepository;
    }

    @Test
    public void createAndDelete() {

    }

    @Test
    public void countById() {

    }
}
