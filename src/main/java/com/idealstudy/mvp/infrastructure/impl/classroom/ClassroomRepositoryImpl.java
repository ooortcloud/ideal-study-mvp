package com.idealstudy.mvp.infrastructure.impl.classroom;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;

import java.util.function.Function;

import com.idealstudy.mvp.mapstruct.ClassroomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClassroomRepositoryImpl implements ClassroomRepository {

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    private final static int SIZE = 10;

    @Override
    public ClassroomResponseDto save(String title, String description, Integer capacity, String thumbnail) {

        ClassroomEntity entity = ClassroomEntity.builder()
                .title(title)
                .description(description)
                .capacity(capacity)
                .thumbnail(thumbnail)
                .build();

        return ClassroomMapper.INSTANCE.toDto(classroomJpaRepository.save(entity));
    }

    @Override
    public ClassroomResponseDto findById(String id) {

        ClassroomEntity entity = classroomJpaRepository.findById(id).orElseThrow();

        return ClassroomMapper.INSTANCE.toDto(entity);
    }

    @Override
    public ClassroomPageResultDto findAll() {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(1)
                .size(SIZE)
                .build();

        Page<ClassroomEntity> pageResult = classroomJpaRepository.findAll(
                requestDto.getPageable(Sort.by("regDate").descending()));

        Function<ClassroomEntity, ClassroomResponseDto> fn = ClassroomMapper.INSTANCE::toDto;

        PageResultDto<ClassroomResponseDto, ClassroomEntity> pageResultDto = new PageResultDto<>(
            pageResult, fn);

        return ClassroomMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public ClassroomResponseDto update(String id, String title, String description, Integer capacity,
                                       String thumbnail) {

        ClassroomEntity entity = classroomJpaRepository.findById(id).orElseThrow();

        if (title != null) {
            entity.setTitle(title);
        }
        if (description != null) {
            entity.setDescription(description);
        }
        if (capacity != null) {
            entity.setCapacity(capacity);
        }
        if (thumbnail != null) {
            entity.setThumbnail(thumbnail);
        }

        return ClassroomMapper.INSTANCE.toDto(classroomJpaRepository.save(entity));
    }

    @Override
    public void deleteById(String id) {

        ClassroomEntity entity = classroomJpaRepository.findById(id).orElseThrow();
        classroomJpaRepository.delete(entity);
    }

    @Override
    public ClassroomPageResultDto findByStudentId(String studentId) {

        return null;
    }
}
