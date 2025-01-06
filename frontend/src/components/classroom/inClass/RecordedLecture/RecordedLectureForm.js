// 인강생성 폼
import React, { useState, useEffect } from "react";
import styles from "./RecordedLectureForm.module.css";
import { createRecordedLecture } from "../../../../services/classroom/RecordedLectureService.mjs";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

// 1. 파일 선택을 하면 vimeo 에서 form 과 uri 를 받는다.
// 2. 인강 생성 버튼을 누르면 스프링 서버에 인강 정보를 저장한다.
// 3. 영상 업로드 버튼을 누르면 vimeo 에 영상을 업로드한다.

// TODO 1. secret key 를 통해 vimeo 토큰 발급받아 프로세스 진행
// TODO 2. 인강 생성시 vimeo 에도 제목, 영상 + 권한 추가
// TODO 3. 인강 생성 실패시 vimeo 임시영상 공간 삭제
// TODO 4. 인강 업로드 중인지 완료중인지 표시
// 이하 페이지 컴포넌트화

const RecordedLectureForm = () => {
  const [formData, setFormData] = useState({
    classroomId: "",
    title: "",
    description: "",
    url: "",
    videoId: null,
    video: null,
  }); // spring 서버 전송용
  const [file, setFile] = useState(null); // vimeo 업로드용
  const [uploadForm, setUploadForm] = useState(null); // vimeo 에서 받은 uri와 form

  const { classId } = useParams();
  const navigate = useNavigate();

  // formData 에 클래스 ID 담기
  useEffect(() => {
    setFormData((prev) => ({ ...prev, classroomId: classId }));
  }, [classId]);

  // 인강정보 입력
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // 비디오 입력
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setFile(file);

    getUploadFormAndUri(file);
  };

  // vimeo form, uri 받기
  const getUploadFormAndUri = async (file) => {
    console.log("ready to upload video");
    try {
      const response = await axios.post(
        "https://api.vimeo.com/me/videos",
        {
          upload: {
            approach: "post",
            size: file.size, // 안넣어도 되는데 vimeo 계정 유형별 제한 문제
            redirect_url: "http://localhost:3000", // 최종 영상 업로드후 리다이렉트될 화면
          },
        },
        {
          headers: {
            Authorization: "Bearer a92c36d3b027110b7658d44945a8037b",
            "Content-Type": "application/json",
            Accept: "application/vnd.vimeo.*+json;version=3.4",
          },
        }
      );

      setUploadForm({
        action: response.data.upload.upload_link, // vimeo CDN 이나 스토리지 서버
        form: response.data.upload.form,
      });

      setFormData((prev) => ({ ...prev, url: response.data.uri }));

      console.log(response);
    } catch (error) {
      console.error("Failed to get upload form:", error);
      alert("영상확정 실패");
    }
  };

  // vimeo 비디오 업로드
  const uploadToVimeo = async (e) => {
    try {
      const formData = new FormData();
      formData.append("file_data", file);

      const response = await axios.post(uploadForm.action, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      console.log(response);
      return response;
    } catch (error) {
      console.error("Failed to upload video:", error);
      alert("영상업로드 실패");
    }
  };

  // spring 백엔드 인강 생성 요청
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file) {
      alert("비디오를 선택해주세요");
      return;
    }

    e.preventDefault();
    console.log(formData);
    console.log(file);

    try {
      // 1. 스프링 서버에 강의 정보 저장
      await createRecordedLecture(formData);

      // 2. 업로드 시작을 알리고 목록 페이지로 이동
      navigate(`/recordedLecture/list/${classId}`, {
        state: { message: "영상이 백그라운드에서 업로드되고 있습니다." },
      });

      // 3. vimeo 비디오 업로드
      await uploadToVimeo();
    } catch (error) {
      console.error("Failed to create recorded lecture:", error);
      alert("인강생성 실패");
    }
  };

  return (
    <div className={styles.recordedLectureFormContainer}>
      <div className={styles.recordedLectureFormHeader}>
        <h1>인강 생성 폼</h1>
      </div>
      <form
        onSubmit={handleSubmit}
        className={styles.recordedLectureFormContent}
      >
        <div className={styles.recordedLectureFormGroup}>
          <label>제목</label>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>
        <div className={styles.recordedLectureFormGroup}>
          <label>설명</label>
          <textarea
            name="description"
            value={formData.description}
            onChange={handleChange}
            required
          />
        </div>
        <div className={styles.recordedLectureFormGroup}>
          <input type="file" onChange={handleFileChange} />
        </div>
        <button type="submit" className={styles.recordedLectureFormSubmit}>
          인강 생성
        </button>
      </form>
    </div>
  );
};

export default RecordedLectureForm;
