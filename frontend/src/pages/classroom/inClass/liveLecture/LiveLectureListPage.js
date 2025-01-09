import React from "react";
import Button from "../../../../components/Button";
import { useNavigate } from "react-router-dom";
import "./LiveLectureListPage.css";
import LiveLectureForm from "../../../../components/classroom/inClass/LiveLecture/LiveLectureForm";

const LiveLectureListPage = () => {
  const navigate = useNavigate();

  return (
    <div>
      <div className="liveLectureListPageHeader">
        <h2>실시간특강 목록</h2>
        <Button onClick={() => navigate("/recordedLecture/new")}>
          실시간특강 생성
        </Button>
      </div>
      <LiveLectureForm />
      <div className="liveLectureListPageItem">
        <h3>제목</h3>
        <p>설명</p>
      </div>
    </div>
  );
};

export default LiveLectureListPage;
