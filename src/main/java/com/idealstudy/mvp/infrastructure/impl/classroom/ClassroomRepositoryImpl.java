package com.idealstudy.mvp.infrastructure.impl.classroom;

import com.idealstudy.mvp.infrastructure.ClassroomRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ClassroomRepositoryImpl implements ClassroomRepository {

    private ClassroomJpaRepository classroomJpaRepository;

    // jpaRepository 이용 실제 메서드 구현

    @Override
    public ClassroomEntity save(ClassroomEntity entity) {
        return null;
    }

    @Override
    public Optional<ClassroomEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<ClassroomEntity> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public boolean existsById(String id) {
        return false;
    }


}
