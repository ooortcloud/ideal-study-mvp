// 인강 목록
import React from "react";
import RecordedLectureItem from "./RecordedLectureItem";
import styles from "./RecordedLectureList.module.css";
import RecordedLectureDetail from "./RecordedLectureDetail";

const RecordedLectureList = ({
  lectures,
  onLectureClick,
  expandedLectureId,
  isClickable,
}) => {
  return (
    <div className={styles.recordedLectureListContainer}>
      {lectures.map((lecture) => (
        <div key={lecture.id}>
          <div
            className={styles.recordedLectureListItem}
            onClick={() => isClickable && onLectureClick(lecture.id)}
            style={{ cursor: isClickable ? "pointer" : "default" }}
          >
            <RecordedLectureItem lecture={lecture} />
          </div>
          {expandedLectureId === lecture.id && (
            <div className={styles.recordedLectureDetail}>
              <RecordedLectureDetail lecture={lecture} />
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default RecordedLectureList;
