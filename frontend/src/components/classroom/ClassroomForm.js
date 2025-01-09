import React, { useState } from "react";
import {
  createClass,
  updateClass,
} from "../../services/classroom/ClassroomService.mjs";
import styles from "./ClassroomForm.module.css";
import Button from "../Button";

const ClassroomForm = ({ initialData = null, onSubmit }) => {
  const [form, setForm] = useState(
    initialData || { title: "", description: "", capacity: "", image: null }
  );

  const handleSubmit = async (e) => {
    e.preventDefault();

    // DTO 객체 생성 및 FormData 객체에 추가
    const formData = new FormData();
    const dto = {
      title: form.title,
      description: form.description,
      capacity: form.capacity,
    };
    formData.append("dto", new Blob([JSON.stringify(dto)], { type: "application/json"})); // DTO를 FormData에 추가

    if (form.thumbnail) {
      const imageBlob = new Blob ([form.thumbnail], {type:form.thumbnail.type })
      formData.append("image", imageBlob, form.thumbnail.name ); // 이미지 별도로 전송
    }
    onSubmit(formData);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <div className={styles.imageContainer}>
        <img src={form.thumbnail} alt="썸네일 이미지" />
      </div>
      <div className={styles.inputContainer}>
        <h2>썸네일 이미지</h2>
        <input
          type="file"
          accept="image/*"
          onChange={(e) => setForm({ ...form, thumbnail: e.target.files[0] })}
        />
        <h2>클래스 제목</h2>
        <input
          type="text"
          placeholder="클래스 제목"
          value={form.title}
          onChange={(e) => setForm({ ...form, title: e.target.value })}
        />
        <h2>클래스 설명</h2>
        <textarea
          placeholder="클래스 설명"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
        />
        <h2>모집 인원</h2>
        <input
          type="number"
          placeholder="모집 인원"
          value={form.capacity}
          onChange={(e) => setForm({ ...form, capacity: e.target.value })}
        />
      </div>
      <Button type="submit">{form.id ? "수정하기" : "생성하기"}</Button>
    </form>
  );
};

export default ClassroomForm;
