import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import {
  readClassById,
  updateClass,
} from "../../services/classroom/ClassroomService.mjs";
import LikeButton from "../../components/LikeButton";
import ClassInfo from "./ClassInfo";
import ClassEnrollment from "../../components/classroom/preClass/enrollment/Enrollment";
import ClassFAQ from "../../components/classroom/preClass/FAQ/FAQBoard";
import ClassInquiry from "./preClass/inquiry/InquiryBoardPage";
import ClassSchedule from "../../components/classroom/inClass/schedule/ScheduleBoard";

import "./ClassroomDetailPage.css";
import PostBoard from "../../components/classroom/inClass/post/PostBoard";
import RecordedLectureListPage from "./inClass/RecordedLecture/RecordedLectureListPage";
import LiveLectureListPage from "./inClass/liveLecture/LiveLectureListPage";
import MaterialListPage from "./inClass/material/MaterialListPage";
import AttendancePage from "./inClass/attendance/AttendancePage";
import AssignmentPage from "./inClass/assessment/Assignment";
import StudyGroupPage from "./inClass/studygroup/StudyGroupPage";
import Button from "../../components/Button";
import useAuthStore from "../../stores/authStore";

import VimeoPlayer from "../../components/vimeo/VimeoPlayer";

const ClassroomDetailPage = () => {
  const { classId } = useParams();
  const [classroomInfo, setClassroomInfo] = useState(null);
  const userInfo = useAuthStore((state) => state.userInfo);

  useEffect(() => {
    // API 호출로 클래스 상세 정보 가져오기
    const fetchClass = async () => {
      const data = await readClassById(classId);

      setClassroomInfo(data);
    };
    fetchClass();
  }, [classId]);

  const handleUpdateClass = async () => {
    const updatedData = {
      // 수정할 데이터 예시
      title: "수정된 클래스 제목",
      description: "수정된 클래스 설명",
    };
    const updatedClass = await updateClass(classId, updatedData);
    setClassroomInfo(updatedClass); // 수정된 클래스 정보로 상태 업데이트
  };

  if (!classroomInfo) return <p>로딩 중...</p>;

  return (
    <div className="classroom-container">
      {/* 수정 */}
      {/* 클래스 정보 섹션 */}
      <div className="section class-info-section">
        <ClassInfo classroom={classroomInfo} />
      </div>
      {userInfo.id === classroomInfo.createdBy && (
        <Button onClick={handleUpdateClass}>수정</Button>
      )}

      {/* 좋아요 버튼 */}
      <div className="section like-button">
        <LikeButton />
      </div>

      {/* 수업 신청 섹션 */}
      <div className="section enrollment-section">
        <ClassEnrollment classId={classId} />
      </div>

      {/* 샘플 영상 test */}
      <div className="section">
        <VimeoPlayer />
      </div>
      
      {/* 질문 게시판 섹션 */}
      {/* <div className="section inquiry-section">
        <ClassInquiry classId={classId} />
      </div> */}

      {/* FAQ 섹션 */}
      {/* <div className="section faq-section">
        <ClassFAQ classId={classId} />
      </div> */}

      {/* 스케줄 섹션 */}
      {/* <div className="section schedule-section">
        <ClassSchedule classId={classId} />
      </div> */}

      {/* 인강 섹션 */}
      {/* <div className="section recordedLecture-section">
        <RecordedLectureListPage />
      </div> */}

      {/* 실시간특강 섹션 */}
      {/* <div className="section liveLecture-section">
        <LiveLectureListPage />
      </div> */}

      {/* 자료 섹션 */}
      {/* <div className="section material-section">
        <MaterialListPage classId = {classId }/>
      </div> */}

      {/* 과제 섹션 */}
      {/* <div className="section assignment-section">
        <AssignmentPage />
      </div> */}

      {/* 시험 섹션 */}
      {/* <div className="section exam-section"></div> */}

      {/* 게시물 섹션 */}
      {/* <div className="section post-section">
        <PostBoard classId={classId} />
      </div> */}

      {/* 출석 섹션 */}
      {/* <div className="section attendance-section">
        <AttendancePage />
      </div> */}

      {/* 스터디 그룹 섹션 */}
      {/* <div className="section studyGroup-section">
        <StudyGroupPage />
      </div> */}
    </div>
  );
};

export default ClassroomDetailPage;
