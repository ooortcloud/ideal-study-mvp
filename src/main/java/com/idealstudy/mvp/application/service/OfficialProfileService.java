package com.idealstudy.mvp.application.service;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.repository.OfficialProfileRepository;
import com.idealstudy.mvp.application.domain_service.ValidationManager;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OfficialProfileService {

    private final OfficialProfileRepository officialProfileRepository;

    private final ValidationManager validationManager;

    @Autowired
    public OfficialProfileService(OfficialProfileRepository officialProfileRepository, ValidationManager validationManager) {
        this.officialProfileRepository = officialProfileRepository;
        this.validationManager = validationManager;
    }

    public OfficialProfileDto create(String teacherId) {

        return TryCatchServiceTemplate.execute(() -> {

            String initContent = getInitContent();

            return officialProfileRepository.create(teacherId, initContent);
        },
        null, DBErrorMsg.CREATE_ERROR);
    }

    public OfficialProfileDto selectOne(String teacherId) {

        return TryCatchServiceTemplate.execute(() -> officialProfileRepository.findByTeacherId(teacherId),
                null, DBErrorMsg.SELECT_ERROR);
    }

    /**
     *
     * @param teacherId : 반드시 토큰 내에 저장된 sub 값을 넣을 것.
     * @param html
     * @return
     */
    public OfficialProfileDto update(String teacherId, String html) {

        return TryCatchServiceTemplate.execute(() -> {
            return officialProfileRepository.update(teacherId, html);
        },
        null, DBErrorMsg.UPDATE_ERROR);
    }

    private String getInitContent() {

        return "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>official profile 예시</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>안녕하세요!</h1>\n" +
                "    <p>official profile 예시입니다.</p>\n" +
                "</body>\n" +
                "</html>";
    }
}
