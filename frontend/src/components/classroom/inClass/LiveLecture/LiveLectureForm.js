import React, { useState } from "react";
import { createLiveLecture } from "../../../../services/classroom/LiveLectureService.mjs";
import styles from "./LiveLectureForm.module.css";

const LiveLectureForm = ({ classId }) => {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    startDate: "",
    duration: "",
    platform: "",
    location: "",
    link: "",
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endTime = new Date(formData.startDate);
    endTime.setMinutes(endTime.getMinutes() + formData.duration);

    const lectureData = {
      ...formData,
      classId,
      startTime: formData.startDate.toISOString(),
      endTime: endTime.toISOString(),
    };

    await createLiveLecture(lectureData);
  };

  return (
    <form onSubmit={handleSubmit} className={styles.form}>
      <input
        type="text"
        name="title"
        placeholder="제목"
        className={styles.input}
      />
      <input
        type="text"
        name="description"
        placeholder="설명"
        className={styles.input}
      />
      <input
        type="text"
        name="startDate"
        placeholder="시작일"
        className={styles.input}
      />
      <input
        type="number"
        name="duration"
        placeholder="소요시간 (분)"
        className={styles.input}
      />
      <select className={styles.select}>
        <option value="ZOOM">ZOOM</option>
        <option value="GOOGLE MEET">GOOGLE MEET</option>
        <option value="오프라인">스터디카페</option>
      </select>
      <input
        type="text"
        name="location"
        placeholder="장소"
        className={styles.input}
      />
      <input
        type="url"
        name="link"
        placeholder="수업 URL"
        className={styles.input}
      />
      <div className={styles.buttonContainer}>
        <button type="submit" className={styles.submitButton}>
          생성
        </button>
        <button type="button" className={styles.cancelButton}>
          취소
        </button>
      </div>
    </form>
  );
};

export default LiveLectureForm;
