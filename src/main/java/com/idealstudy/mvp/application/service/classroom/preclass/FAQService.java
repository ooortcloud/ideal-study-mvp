package com.idealstudy.mvp.application.service.classroom.preclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;
import com.idealstudy.mvp.application.repository.preclass.FAQRepository;
import com.idealstudy.mvp.application.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FAQService {

    private final static Integer PAGE_SIZE = 10;

    @Autowired
    private final FAQRepository faqRepository;

    @Autowired
    private final ValidationManager validationManager;

    public FAQDto create(String teacherId, String classroomId, String title, String content) {

        return TryCatchServiceTemplate.execute(() ->
                        faqRepository.create(title, content, classroomId, teacherId),
                null, DBErrorMsg.CREATE_ERROR);
    }

    public FAQDto findById(Long faqId) {

        return faqRepository.findById(faqId);
    }

    public FAQPageResultDto findList(int page, String classroomId) {

        PageRequestDto dto = PageRequestDto.builder()
                .page(page)
                .size(PAGE_SIZE)
                .build();

        return faqRepository.findList(dto, classroomId);
    }

    public FAQDto update(Long faqId, String title, String content, String teacherId) {

        return TryCatchServiceTemplate.execute(() -> {

            FAQDto dto = findById(faqId);
            String classroomId = dto.getClassroomId();

            validationManager.validateTeacher(teacherId, classroomId);

            return faqRepository.update(faqId, title, content);
        }, null, DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long faqId, String teacherId) {

        TryCatchServiceTemplate.execute(() -> {

            FAQDto dto = findById(faqId);
            String classroomId = dto.getClassroomId();

            validationManager.validateTeacher(teacherId, classroomId);

            faqRepository.delete(faqId);

            return null;
        }, null, DBErrorMsg.DELETE_ERROR);
    }
}
