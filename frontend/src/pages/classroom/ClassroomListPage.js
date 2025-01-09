import React, { useEffect, useState } from "react";
import ClassroomCard from "../../components/classroom/ClassroomCard";
import { readClasses } from "../../services/classroom/ClassroomService.mjs";
import { useNavigate } from "react-router-dom";
import "./ClassroomListPage.css";
import Button from "../../components/Button";

const ClassroomListPage = () => {
  const [classrooms, setClassrooms] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // API 호출로 클래스 목록 가져오기
    const fetchClasses = async () => {
      const data = await readClasses();
      setClassrooms(data.dtoList);
    };
    fetchClasses();
  }, []);

  const handleCreateClass = () => {
    navigate("/classes/new");
  };

  return (
    <div>
      <div className="classroom-list-header">
        <h2>클래스 목록</h2>
        <Button onClick={handleCreateClass}>클래스 생성</Button>
      </div>

      <div className="classroom-grid">
        {classrooms.map((classroom) => (
          <ClassroomCard key={classroom.id} classroom={classroom} />
        ))}
      </div>
    </div>
  );
};

export default ClassroomListPage;
