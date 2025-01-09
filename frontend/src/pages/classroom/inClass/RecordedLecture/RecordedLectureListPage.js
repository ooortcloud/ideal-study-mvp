import React, { useEffect, useState } from "react";
import RecordedLectureList from "../../../../components/classroom/inClass/RecordedLecture/RecordedLectureList";
import { readRecordedLecturesByClassId } from "../../../../services/classroom/RecordedLectureService.mjs";
import Button from "../../../../components/Button";
import { useNavigate, useParams } from "react-router-dom";
import "./RecordedLectureListPage.css";
const RecordedLectureListPage = () => {
  const [lectures, setLectures] = useState([]);
  const navigate = useNavigate();
  const isLoggedIn = true; // TODO: 실제 로그인 상태 확인 로직으로 교체
  const [expandedLectureId, setExpandedLectureId] = useState(null); // 클릭시 펼칠 강의
  const { classId } = useParams();

  useEffect(() => {
    const fetchLectures = async () => {
      const lectures = await readRecordedLecturesByClassId(classId);
      setLectures(lectures.dtoList);
    };
    fetchLectures();
  }, [classId]);

  const handleLectureExpand = (lectureId) => {
    if (isLoggedIn) {
      if (expandedLectureId === lectureId) {
        setExpandedLectureId(null);
      } else {
        setExpandedLectureId(lectureId);
      }
    }
    // 비회원이면 아무 동작 안 함
  };

  return (
    <div>
      <div className="recordedLectureListPageHeader">
        <h2> 인강 목록 </h2>
        {isLoggedIn && (
          <Button onClick={() => navigate(`/recordedLecture/new/${classId}`)}>
            인강 생성
          </Button>
        )}
      </div>
      <RecordedLectureList
        lectures={lectures}
        onLectureClick={handleLectureExpand} // 클릭시 동작할 함수 (자식에서 클릭함)
        expandedLectureId={expandedLectureId} // 클릭시 펼칠 강의 (자식에서 클릭할 강의)
        isClickable={isLoggedIn} // 비회원이면 클릭 불가능
      />
    </div>
  );
};

export default RecordedLectureListPage;
