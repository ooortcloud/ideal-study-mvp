package com.idealstudy.mvp.application.service.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.application.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.application.repository.preclass.ClassInquiryRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ClassInquiryService {

    @Autowired
    private final ClassInquiryRepository classInquiryRepository;

    @Autowired
    private final ValidationManager validationManager;

    public ClassInquiryDto create(String title, String content, String classroomId,Visibility visibility) {

        return TryCatchServiceTemplate.execute(() ->
            classInquiryRepository.create(title, content, classroomId, visibility)
        , null, DBErrorMsg.CREATE_ERROR);
    }

    public ClassInquiryDto findById(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);
        
        // 클래스 내 강사와 해당 글 작성 학생만 조회 가능함
        if(dto.getVisibility().equals(Visibility.PRIVATE)) {
            if( userId != null)
                checkMine(classInquiryId, userId);
            // 회원 정보 없으면 무조건 거부
            else {
                log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
                throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            }
        }
        return dto;
    }

    public ClassInquiryPageResultDto findListByClassId(String classId, int page) {

        return TryCatchServiceTemplate.execute(() -> classInquiryRepository.findListByClassId(classId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ClassInquiryPageResultDto findListByMemberId(String userId, int page){

        return TryCatchServiceTemplate.execute(() -> classInquiryRepository.findListByMemberId(userId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }


    public ClassInquiryDto update(Long classInquiryId, String title, String content, String classroomId,
                                  Visibility visibility, String userId) {

        return TryCatchServiceTemplate.execute(() -> {

            ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);

            validationManager.validateIndividual(userId, dto.getCreatedBy());

            return classInquiryRepository.update(classInquiryId, title, content,
                            classroomId, visibility);
            }, null, DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long inquiryId, String deleter) {

        TryCatchServiceTemplate.execute(() -> classInquiryRepository.delete(inquiryId),
                ()-> checkMine(inquiryId, deleter), DBErrorMsg.DELETE_ERROR);
    }

    private void checkMine(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);

        if(dto.getCreatedBy().equals(userId)) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        }
    }
}
