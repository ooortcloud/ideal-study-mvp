// 인강 상세설명

import React from "react";
import VimeoPlayer from "../../../../components/vimeo/VimeoPlayer";
import styles from "./RecordedLectureDetail.module.css";

const RecordedLectureDetail = ({lecture}) => {
  const splitedVideoId = lecture.url.split('/')[2];
  console.log(splitedVideoId);

  return (
    <div className={styles.recordedLectureDetailContainer}>
      <div className={styles.recordedLectureDetailHeader}>
        <h1>강의 제목</h1>
        <p>강의 설명</p>
      </div>
      <div className={styles.recordedLectureDetailInfo}>
        <div className={styles.recordedLectureDetailVideo}>
          <VimeoPlayer videoId={splitedVideoId} />
        </div>
      </div>
    </div>
  );
};

export default RecordedLectureDetail;
