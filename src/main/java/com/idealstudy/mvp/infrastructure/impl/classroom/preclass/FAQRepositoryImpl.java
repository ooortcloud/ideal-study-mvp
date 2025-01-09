package com.idealstudy.mvp.infrastructure.impl.classroom.preclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.application.repository.preclass.FAQRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.preclass.FAQEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.preclass.FAQJpaRepository;
import com.idealstudy.mvp.mapstruct.FAQMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FAQRepositoryImpl implements FAQRepository {

    @Autowired
    private final FAQJpaRepository faqJpaRepository;

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final FAQMapper faqMapper;

    @Override
    public FAQDto create(String title, String content, String classroomId, String createdBy) {

        ClassroomEntity classroomEntity = classroomJpaRepository.findById(classroomId).orElseThrow();

        FAQEntity entity = FAQEntity.builder()
                .classroom(classroomEntity)
                .title(title)
                .content(content)
                .build();

        return FAQMapper.INSTANCE.entityToDto(faqJpaRepository.save(entity));
    }

    @Override
    public FAQDto findById(Long faqId) {

        FAQEntity entity = faqJpaRepository.findById(faqId).orElseThrow();

        return faqMapper.entityToDto(entity);
    }

    @Override
    public FAQPageResultDto findList(PageRequestDto dto, String classroomId) {

        Pageable pageable = dto.getPageable(Sort.by("regDate").ascending());

        Page<FAQEntity> results = faqJpaRepository.findByClassroom_classroomId(classroomId, pageable);

        Function<FAQEntity, FAQDto> fn = faqMapper::entityToDto;

        return faqMapper.toPageResultDto(new PageResultDto<FAQDto, FAQEntity>(results, fn));
    }

    @Override
    public FAQDto update(Long id, String title, String content) {

        FAQEntity entity = faqJpaRepository.findById(id).orElseThrow();

        if(title != null)
            entity.setTitle(title);

        if(content != null)
            entity.setContent(content);

        FAQEntity result = faqJpaRepository.save(entity);
        return faqMapper.entityToDto(result);
    }

    @Override
    public void delete(Long faqId) {

        FAQEntity entity = faqJpaRepository.findById(faqId).orElseThrow();
        faqJpaRepository.delete(entity);
    }
}
