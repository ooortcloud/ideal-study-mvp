package com.idealstudy.mvp.application.repository.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.enums.classroom.Visibility;

public interface ClassInquiryRepository {

    ClassInquiryDto create(String title, String content, String classroomId, Visibility visibility);

    ClassInquiryPageResultDto findListByClassId(String classId, int page);

    ClassInquiryPageResultDto findListByMemberId(String userId, int page);

    ClassInquiryDto findById(Long inquiryId);

    ClassInquiryDto update(Long inquiryId, String title, String content, String classroomId, Visibility visibility);

    boolean delete(Long inquiryId);
}
