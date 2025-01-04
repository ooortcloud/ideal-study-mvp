package com.idealstudy.mvp.infrastructure.impl.classroom.preclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.application.repository.preclass.ClassInquiryRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.ClassInquiryEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.preclass.ClassInquiryJpaRepository;
import com.idealstudy.mvp.mapstruct.ClassInquiryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ClassInquiryRepositoryImpl implements ClassInquiryRepository {

    private final int SIZE = 10;

    @Autowired
    private final ClassInquiryJpaRepository classInquiryJpaRepository;

    @Autowired
    private final ClassInquiryMapper classInquiryMapper;

    @Override
    public ClassInquiryDto create(String title, String content, String classroomId,
                                  Visibility visibility) {

        ClassInquiryDto dto = ClassInquiryDto.builder()
                .title(title)
                .content(content)
                .classroomId(classroomId)
                .visibility(visibility)
                .build();

        ClassInquiryEntity entity = classInquiryMapper.dtoToEntity(dto);

        return classInquiryMapper.entityToDto(classInquiryJpaRepository.save(entity));
    }

    @Override
    public ClassInquiryPageResultDto findListByClassId(String classId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<ClassInquiryEntity> resultPage = classInquiryJpaRepository.findByClassroom_classroomId(classId,
                 requestDto.getPageable(Sort.by("regDate")));

        Function<ClassInquiryEntity, ClassInquiryDto> fn = classInquiryMapper::entityToDto;

        PageResultDto<ClassInquiryDto, ClassInquiryEntity>resultDto =
                new PageResultDto<ClassInquiryDto, ClassInquiryEntity>(resultPage, fn);

        return classInquiryMapper.toPageResultDto(resultDto);
    }

    @Override
    public ClassInquiryPageResultDto findListByMemberId(String userId, int page) {
        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<ClassInquiryEntity> resultPage = classInquiryJpaRepository.findByCreatedBy(userId,
                requestDto.getPageable(Sort.by("regDate")));

        Function<ClassInquiryEntity, ClassInquiryDto> fn = classInquiryMapper::entityToDto;

        PageResultDto<ClassInquiryDto, ClassInquiryEntity>resultDto =
                new PageResultDto<ClassInquiryDto, ClassInquiryEntity>(resultPage, fn);

        return classInquiryMapper.toPageResultDto(resultDto);
    }

    // private row는 본인만 조회할 수 있게 application layer에서 방어해야 함.
    @Override
    public ClassInquiryDto findById(Long inquiryId) {

        ClassInquiryEntity entity = classInquiryJpaRepository.findById(inquiryId).orElseThrow();
        log.info("클래스 정보: " + entity.getClassroom());
        log.info("작성자 정보: " + entity.getCreatedBy());
        return classInquiryMapper.entityToDto(entity);
    }

    // 본인이 아니면 수정할 수 없게 application layer에서 방어해야 함.
    @Override
    public ClassInquiryDto update(Long id, String title, String content, String classroomId,
                                  Visibility visibility) {

        ClassInquiryDto dto = ClassInquiryDto.builder()
                .title(title)
                .content(content)
                .classroomId(classroomId)
                .classroomId(classroomId)
                .visibility(visibility)
                .build();

        ClassInquiryEntity entity = classInquiryJpaRepository.findById(id).orElseThrow();

        classInquiryMapper.updateEntity(dto, entity);

        return classInquiryMapper.entityToDto(classInquiryJpaRepository.save(entity));
    }

    // 본인이 아니면 삭제할 수 없게 application layer에서 방어해야 함.
    @Override
    public boolean delete(Long inquiryId) {

        ClassInquiryEntity entity = classInquiryJpaRepository.findById(inquiryId).orElseThrow();
        try {
            classInquiryJpaRepository.delete(entity);
        } catch (Exception e) {
            log.error("삭제 중 문제 발생: " + e + " : " + e.getMessage());
            return false;
        }

        return true;
    }
}
