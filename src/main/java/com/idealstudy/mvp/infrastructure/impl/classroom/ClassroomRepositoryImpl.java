package com.idealstudy.mvp.infrastructure.impl.classroom;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;

import java.util.function.Function;

import com.idealstudy.mvp.infrastructure.jpa.repository.member.TeacherJpaRepository;
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

    @Autowired
    private final TeacherJpaRepository teacherJpaRepository;

    private final static int SIZE = 10;

    @Override
    public ClassroomResponseDto save(String title, String description, Integer capacity, String thumbnail,
                                     String teacherId) {

        TeacherEntity teacher = teacherJpaRepository.findById(teacherId).orElseThrow();

        ClassroomEntity entity = ClassroomEntity.builder()
                .teacher(teacher)
                .title(title)
                .description(description)
                .capacity(capacity)
                .thumbnail(thumbnail)
                .status(ClassroomStatus.OPEN)
                .build();

        return ClassroomMapper.INSTANCE.toDto(classroomJpaRepository.save(entity));
    }

    @Override
    public ClassroomResponseDto findById(String id) {

        ClassroomEntity entity = classroomJpaRepository.findById(id).orElseThrow();

        return ClassroomMapper.INSTANCE.toDto(entity);
    }

    @Override
    public ClassroomPageResultDto findAll(int page, ClassroomStatus status) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<ClassroomEntity> pageResult = null;

        if(status == null)
            pageResult = classroomJpaRepository.findAll(
                requestDto.getPageable(Sort.by("regDate").descending()));

        if(status != null)
            pageResult = classroomJpaRepository.findByStatus(status,
                    requestDto.getPageable(Sort.by("regDate").descending()));

        Function<ClassroomEntity, ClassroomResponseDto> fn = ClassroomMapper.INSTANCE::toDto;

        PageResultDto<ClassroomResponseDto, ClassroomEntity> pageResultDto = new PageResultDto<>(
            pageResult, fn);

        return ClassroomMapper.INSTANCE.toPageResultDto(pageResultDto);
    }

    @Override
    public ClassroomResponseDto update(String id, String title, String description, Integer capacity,
                                       String thumbnailUri) {

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
        if (thumbnailUri != null) {
            entity.setThumbnail(thumbnailUri);
        }

        return ClassroomMapper.INSTANCE.toDto(classroomJpaRepository.save(entity));
    }

    @Override
    public void deleteById(String id) {

        ClassroomEntity entity = classroomJpaRepository.findById(id).orElseThrow();
        classroomJpaRepository.delete(entity);
    }
}
