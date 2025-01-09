package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.RecordLectureEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.RecordLectureJpaRepository;
import com.idealstudy.mvp.application.repository.inclass.RecordLectureRepository;
import com.idealstudy.mvp.mapstruct.RecordLectureMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
@Slf4j
@RequiredArgsConstructor
public class RecordLectureRepositoryImpl implements RecordLectureRepository {

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final RecordLectureJpaRepository recordLectureJpaRepository;

    private static final int SIZE = 1000;

    @Override
    public RecordLectureDto create(String classroomId, String title, String description, String videoEndPoint,
                                   Integer order) {

        order = null;

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        RecordLectureEntity entity = RecordLectureEntity.builder()
                .classroom(classroom)
                .orderNum(order)
                .title(title)
                .description(description)
                .url(videoEndPoint)
                .build();

        RecordLectureEntity resultEntity = recordLectureJpaRepository.save(entity);
        log.info("저장된 엔티티: " + resultEntity);
        return RecordLectureMapper.INSTANCE.toDto(resultEntity);
    }

    @Override
    public RecordLectureDto getDetail(Long id) {

        RecordLectureEntity entity = recordLectureJpaRepository.findById(id).orElseThrow();

        return RecordLectureMapper.INSTANCE.toDto(entity);
    }

    @Override
    public RecordLecturePageResultDto selectList(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)  // 인강에서는 페이징 기능이 필요없음. 하지만 정렬 기능 때문에 PageRequest 사용 필요
                .size(SIZE)
                .build();

        // 순서에 대한 논의 중이므로, 현재는 먼저 등록한 것부터 출력되도록 함.
        Page<RecordLectureEntity> pageResult = recordLectureJpaRepository.findByClassroom_ClassroomId(classroomId,
                requestDto.getPageable(Sort.by("regDate").ascending()));

        Function<RecordLectureEntity, RecordLectureDto> fn = RecordLectureMapper.INSTANCE::toDto;

        PageResultDto<RecordLectureDto, RecordLectureEntity> pageResultDto =
                new PageResultDto<RecordLectureDto, RecordLectureEntity>(pageResult, fn);

        return RecordLectureMapper.INSTANCE.toPageResultDto(pageResultDto);
    }


    @Override
    public void updateOrder(Long id, int order) {

        // 순서 구현 방안에 대해서 아직 논의 중
    }

    @Override
    public RecordLectureDto update(Long id, String title, String description, String videoEndPoint, Integer order) {

        order = null;

        RecordLectureEntity entity = recordLectureJpaRepository.findById(id).orElseThrow();
        if (title != null) {
            entity.setTitle(title);
        }
        if (description != null) {
            entity.setDescription(description);
        }
        if (videoEndPoint != null) {
            entity.setUrl(videoEndPoint);
        }

        return RecordLectureMapper.INSTANCE.toDto(recordLectureJpaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {

        RecordLectureEntity entity = recordLectureJpaRepository.findById(id).orElseThrow();

        recordLectureJpaRepository.delete(entity);
    }
}
