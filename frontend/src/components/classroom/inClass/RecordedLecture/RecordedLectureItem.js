// 인강 목록에 띄울 아이템 하나
import React from "react";
import styles from "./RecordedLectureItem.module.css";

const RecordedLectureItem = ({ lecture }) => {
  return (
    <div className={styles.recordedLectureItem}>
      <div className={styles.recordedLectureItemTitle}>
        <h3>{lecture.title}</h3>
      </div>
      <div className={styles.recordedLectureItemDesc}>
        <p>{lecture.description}</p>
      </div>
    </div>
  );
};

export default RecordedLectureItem;
