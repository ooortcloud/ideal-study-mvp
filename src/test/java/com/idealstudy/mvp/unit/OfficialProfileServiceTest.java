package com.idealstudy.mvp.unit;

import com.idealstudy.mvp.unit.util.TestServiceUtil;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.application.repository.OfficialProfileRepository;
import com.idealstudy.mvp.application.service.OfficialProfileService;
import com.idealstudy.mvp.application.service.domain_service.ValidationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OfficialProfileServiceTest {

    @Mock
    private OfficialProfileRepository officialProfileRepository;

    @Mock
    private ValidationManager validationManager;

    @InjectMocks
    private OfficialProfileService officialProfileService;

    /// 당장은 테스트할 가치가 있는 메소드가 없음.
}
