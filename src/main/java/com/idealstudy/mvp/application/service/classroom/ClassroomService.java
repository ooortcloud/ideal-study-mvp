package com.idealstudy.mvp.application.service.classroom;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.application.service.domain_service.FileManager;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.application.repository.ClassroomRepository;
import com.idealstudy.mvp.application.repository.LikedRepository;
import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;

import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@Slf4j
@Transactional
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final LikedRepository likedRepository;

    private final ClassroomComponent classroomComponent;

    private final FileManager fileManager;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository,
                            @Qualifier("likedClassroomRepositoryImpl") LikedRepository likedRepository,
                            ClassroomComponent classroomComponent,
                            @Value("${upload.classroom-thumbnail-path}") String uploadPath) {
        this.classroomRepository = classroomRepository;
        this.likedRepository = likedRepository;
        this.classroomComponent = classroomComponent;
        this.fileManager = new FileManager(uploadPath);
    }

    public ClassroomResponseDto createClassroom(ClassroomRequestDto request, String teacherId, InputStream is) {

        return TryCatchServiceTemplate.execute(() -> {

            String uri = fileManager.saveFile(is, "");

            return classroomRepository.save(request.getTitle(),
                            request.getDescription(), request.getCapacity(), uri, teacherId);
        }
        , null, DBErrorMsg.CREATE_ERROR);
    }

    public ClassroomPageResultDto getAllClassrooms(int page, ClassroomStatus status) {

        return TryCatchServiceTemplate.execute(() -> classroomRepository.findAll(page, status),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ClassroomResponseDto getClassroomById(String id) {

        return TryCatchServiceTemplate.execute(() -> classroomRepository.findById(id),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ClassroomResponseDto updateClassroom(String id, ClassroomRequestDto request, String teacherId,
                                                InputStream is) {

        return TryCatchServiceTemplate.execute(() -> {

            String uri = null;
            if(is != null) {

                String oldUri = classroomRepository.findById(id).getThumbnail();
                fileManager.deleteFile(oldUri);

                uri = fileManager.saveFile(is, "");
            }

            return classroomRepository.update(id,request.getTitle(),
                            request.getDescription(), request.getCapacity(), uri);
        },
        () -> classroomComponent.checkMyClassroom(teacherId, id), DBErrorMsg.UPDATE_ERROR);
    }

    public void deleteClassroom(String classroomId, String teacherId) {

        TryCatchServiceTemplate.execute(() -> {
            classroomRepository.deleteById(classroomId);
            return null;
        },
        () -> classroomComponent.checkMyClassroom(teacherId, classroomId), DBErrorMsg.DELETE_ERROR);
    }

    public void updateLiked() {


    }

    public int countLiked(Long classroomId) {

        String collection = "classrooms";
        return likedRepository.countById(classroomId);
    }
}
