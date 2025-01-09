package com.idealstudy.mvp.application.repository.preclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;

public interface FAQRepository {

    FAQDto create(String title, String content, String classroomId, String createdBy);

    FAQDto findById(Long faqId);

    FAQPageResultDto findList(PageRequestDto dto, String classroomId);

    FAQDto update(Long id, String title, String content);

    void delete(Long faqId);
}
