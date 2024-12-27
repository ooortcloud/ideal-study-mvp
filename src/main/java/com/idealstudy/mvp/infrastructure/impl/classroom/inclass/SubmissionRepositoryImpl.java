package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionFeedbackListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionListResponseDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.submission.SubmissionPageResultDto;
import com.idealstudy.mvp.application.repository.inclass.SubmissionRepository;
import com.idealstudy.mvp.enums.classroom.AssessmentType;
import com.idealstudy.mvp.enums.classroom.SubmissionStatus;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.SubmissionEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.SubmissionJpaRepository;
import com.idealstudy.mvp.mapstruct.SubmissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Slf4j
public class SubmissionRepositoryImpl implements SubmissionRepository {

    private final SubmissionJpaRepository submissionJpaRepository;

    private final DataSource dataSource;

    private final static int SIZE = 10;

    @Autowired
    public SubmissionRepositoryImpl(SubmissionJpaRepository submissionJpaRepository, DataSource dataSource) {
        this.submissionJpaRepository = submissionJpaRepository;
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Long assessmentId, List<String> studentList, AssessmentType type,
                       LocalDateTime startedAt, LocalDateTime endedAt)
            throws SQLException {
        
        /*
         JPA와 같은 Datasource를 사용하기 때문에 동일한 트랜잭션 컨텍스트에서 작업이 이뤄지므로,
         이 JDBC 코드도 @Transactional에 포함되어 동작하게 됨.
         현재 Spring이 관리하는 Connection을 주입받아야 하기 때문에, DataSourceUtils에서 Connection 객체를 가져와야 한다.
        */
        Connection connection = DataSourceUtils.getConnection(dataSource);

        String sql = "INSERT INTO submission " +
        "(student_id, assessment_id, assessment_type, started_at, ended_at, status, reg_date) " +
        "VALUES (?, ?, ?, ?, ?, 'PENDING', NOW())";
        PreparedStatement ps = connection.prepareStatement(sql);

        // batch 작업 진행
        // 학생 정원이 100명을 넘을 경우가 없으므로 분할 배치 처리를 하지는 않겠음.
        for(String studentId : studentList) {

            ps.setString(1, studentId);
            ps.setLong(2, assessmentId);
            ps.setString(3, type.name());
            ps.setTimestamp(4, Timestamp.valueOf(startedAt));
            ps.setTimestamp(5, Timestamp.valueOf(endedAt));

            ps.addBatch();
        }

        ps.executeBatch();
    }

    @Override
    public SubmissionDto findById(Long id) {
        return null;
    }

    @Override
    public SubmissionPageResultDto<SubmissionListResponseDto> findByStudentId(String studentId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<SubmissionEntity> result = submissionJpaRepository.findByStudent_userIdAndSubmissionTextIsNotNullOrSubmissionUriIsNotNull(studentId,
                requestDto.getPageable(Sort.by("id").descending()));

        PageResultDto<SubmissionListResponseDto, SubmissionEntity> pageResultDto =
                new PageResultDto<>(result, SubmissionMapper.INSTANCE::toListResponseDto);

        return SubmissionMapper.INSTANCE.toPageResultDtoWithSubmissionListResponseDto(pageResultDto);
    }

    @Override
    public SubmissionPageResultDto<SubmissionListResponseDto> findByClassroomId(String classroomId, int page) {

        PageRequestDto requestDto = PageRequestDto.builder()
                .page(page)
                .size(SIZE)
                .build();

        Page<SubmissionEntity> entityPage = submissionJpaRepository.findByAssessment_classroom_classroomId(classroomId,
                requestDto.getPageable(Sort.by("regDate").descending()));

        PageResultDto<SubmissionListResponseDto, SubmissionEntity> pageResultDto =
                new PageResultDto<>(entityPage, SubmissionMapper.INSTANCE::toListResponseDto);

        return SubmissionMapper.INSTANCE.toPageResultDtoWithSubmissionListResponseDto(pageResultDto);
    }

    @Override
    public SubmissionDto findByAssessmentIdAndStudentId(Long assessmentId, String studentId) {

        SubmissionEntity entity = submissionJpaRepository
                .findByAssessment_idAndStudent_userId(assessmentId, studentId).orElseThrow();

        return SubmissionMapper.INSTANCE.toDto(entity);
    }

    @Override
    public SubmissionPageResultDto<SubmissionFeedbackListResponseDto> findPersonalityFeedbackListByStudentId(
            String studentId, int page) {

        

        return null;
    }

    @Override
    public SubmissionPageResultDto<SubmissionFeedbackListResponseDto> findPersonalityFeedbackListByClassroomId(
            String classroomId, int page) {
        return null;
    }


    @Override
    public SubmissionDto updateStatus(Long id, SubmissionStatus status) {

        SubmissionEntity entity = submissionJpaRepository.findById(id).orElseThrow();
        entity.setStatus(status);
        return SubmissionMapper.INSTANCE.toDto(submissionJpaRepository.save(entity));
    }

    @Override
    public SubmissionDto updateSubmissionText(Long id, String text) {

        SubmissionEntity entity = submissionJpaRepository.findById(id).orElseThrow();
        entity.setSubmissionText(text);
        entity.setStatus(SubmissionStatus.COMPLETED);

        return SubmissionMapper.INSTANCE.toDto(submissionJpaRepository.save(entity));
    }

    @Override
    public SubmissionDto updateSubmissionUri(Long id, String uri) {

        SubmissionEntity entity = submissionJpaRepository.findById(id).orElseThrow();
        entity.setSubmissionUri(uri);
        entity.setStatus(SubmissionStatus.COMPLETED);

        return SubmissionMapper.INSTANCE.toDto(submissionJpaRepository.save(entity));
    }

    @Override
    public SubmissionDto updateScore(Long assessmentId, String studentId, int score) {

        SubmissionEntity entity = submissionJpaRepository.findByAssessment_idAndStudent_userId(assessmentId, studentId)
                .orElseThrow();
        entity.setScore(score);

        return SubmissionMapper.INSTANCE.toDto(submissionJpaRepository.save(entity));
    }

    @Override
    public SubmissionDto updatePersonalFeedback(Long assessmentId, String studentId, String feedback) {

        SubmissionEntity entity = submissionJpaRepository
                .findByAssessment_idAndStudent_userId(assessmentId, studentId).orElseThrow();

        entity.setPersonalFeedback(feedback);

        return SubmissionMapper.INSTANCE.toDto(submissionJpaRepository.save(entity));
    }
}
