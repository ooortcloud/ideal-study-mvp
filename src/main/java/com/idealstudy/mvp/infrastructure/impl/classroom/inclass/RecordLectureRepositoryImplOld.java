package com.idealstudy.mvp.infrastructure.impl.classroom.inclass;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLectureDto;
import com.idealstudy.mvp.application.dto.classroom.inclass.RecordLecturePageResultDto;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.inclass.RecordLectureEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.inclass.RecordLectureJpaRepository;
import com.idealstudy.mvp.application.repository.inclass.RecordLectureRepository;
import com.idealstudy.mvp.mapstruct.RecordLectureMapper;
import com.idealstudy.mvp.util.TryCatchVimeoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 프론트 쪽에서 동영상을 전송하기로 결정.
 */
@Deprecated
@Slf4j
public class RecordLectureRepositoryImplOld implements RecordLectureRepository {

    private final RecordLectureJpaRepository recordLectureJpaRepository;

    private final ClassroomJpaRepository classroomJpaRepository;

    private final Vimeo vimeo;

    private final String domain;

    @Autowired
    public RecordLectureRepositoryImplOld(RecordLectureJpaRepository recordLectureJpaRepository,
                                          ClassroomJpaRepository classroomJpaRepository,
                                          @Value("${vimeo.personal-access-token}") String accessToken,
                                          @Value("${server.backend-domain}") String domain) {
        this.recordLectureJpaRepository = recordLectureJpaRepository;
        this.classroomJpaRepository = classroomJpaRepository;
        this.domain = domain;

        vimeo = new Vimeo(accessToken);
    }

    @Override
    public RecordLectureDto create(String classroomId, String title, String description, File file, int order) {

        // video upload와 DB 저장 로직을 분리하는 것이 좋음. 현재 로직은 좋은 로직이 아니다.
        String videoEndPoint = TryCatchVimeoTemplate.execute(() -> addVideo(file, title, description));

        ClassroomEntity classroom = classroomJpaRepository.findById(classroomId).orElseThrow();

        RecordLectureEntity entity = RecordLectureEntity.builder()
                .classroom(classroom)
                .orderNum(order)
                .title(title)
                .description(description)
                .url(videoEndPoint)
                .build();

        RecordLectureEntity resultEntity = recordLectureJpaRepository.save(entity);
        log.info("저장된 엔티티: " + resultEntity);
        return RecordLectureMapper.INSTANCE.toDto(resultEntity);
    }

    @Override
    public RecordLectureDto create(String classroomId, String title, String description, String videoEndPoint, Integer order) {
        return null;
    }

    @Override
    public RecordLectureDto getDetail(Long id) {
        return null;
    }

    @Override
    public RecordLecturePageResultDto selectList(String classroomId, int page) {
        return null;
    }

    @Override
    public void updateVideo(Long id, String title, String description, String videoUrl) {

        TryCatchVimeoTemplate.execute(() -> {
            editVideo(videoUrl, title, description);
            return null;
        });
    }

    @Override
    public void updateOrder(Long id, int order) {

    }

    @Override
    public RecordLectureDto update(Long id, String title, String description, String videoEndPoint, Integer order) {
        return null;
    }

    @Override
    public void deleteVideo(Long id, String videoEndPoint) {

        TryCatchVimeoTemplate.execute(() -> {
            vimeo.removeVideo(videoEndPoint);
            return null;
        });
    }

    @Override
    public void delete(Long id) {

    }

    private String addVideo(File file, String name, String desc) {

        VimeoResponse response = createVideo(file, name, desc);
        String videoEntPoint = uploadVideo(response, file);
        return videoEntPoint;
    }

    private String getTranscodeStatus(String videoEndPoint) {
        return TryCatchVimeoTemplate.execute(() ->
                vimeo.getVideoInfo(videoEndPoint).getJson().getString("transcode.status"));
    }

    private void editVideo(String videoEndPoint, String name, String desc) throws IOException {

        final String effName = name;
        final String effDesc = desc;

        JSONObject json = vimeo.getVideoInfo(videoEndPoint).getJson();
        if(name == null)
            name = vimeo.getVideoInfo(videoEndPoint).getJson().getString("name");
        if(desc == null)
            desc = vimeo.getVideoInfo(videoEndPoint).getJson().getString("description");

        String license = "";  // see Vimeo API Docs
        String privacyView = "nobody";  // TODO: 유료 모델 사용 시 disable로 변경할 것
        String privacyEmbed = "whitelist";
        boolean reviewLink = false;


        TryCatchVimeoTemplate.execute(() -> vimeo.updateVideoMetadata(videoEndPoint,
                effName, effDesc, license, privacyView, privacyEmbed, reviewLink));
    }

    private VimeoResponse createVideo(File file, String name, String desc) {

        long fileSize = file.length();
        String privacyView = "nobody";  // TODO: 유료 모델 사용 시 disable로 변경할 것
        String privacyEmbed = "whitelist";

        Map<String, String> params = new HashMap<String, String>();
        params.put("upload.approach", "tus");
        params.put("upload.size", fileSize + "");
        params.put("name", name);
        params.put("description", desc);
        params.put("license", "");  // see Vimeo API Docs
        params.put("privacy.view", privacyView);
        params.put("privacy.embed", privacyEmbed);
        params.put("review_page.active", "false");

        return TryCatchVimeoTemplate.execute(() ->
                vimeo.post("/me/videos", params, (Map) null));
    }

    private String uploadVideo(VimeoResponse response, File file) {

        String videoEndpoint = null;
        if (response.getStatusCode() == 200) {
            log.info("video upload 시작");
            JSONObject upload = response.getJson().getJSONObject("upload");
            if ("tus".equalsIgnoreCase(upload.getString("approach"))) {
                TryCatchVimeoTemplate.execute(() ->
                    vimeo.uploadVideo(upload.getString("upload_link"), file)
                );
                videoEndpoint = response.getJson().getString("uri");
                log.info("Upload finished. Update videoEndPoint: " + videoEndpoint);

                String domain = URLEncoder.encode(this.domain, StandardCharsets.UTF_8);

                final String effVideoEndpoint = videoEndpoint;
                TryCatchVimeoTemplate.execute(() ->
                        vimeo.put(effVideoEndpoint + "/privacy/domains/" + domain));
            }
        } else {
            log.error("전송 설정 실패... " + response);
        }

        return videoEndpoint;
    }
}
