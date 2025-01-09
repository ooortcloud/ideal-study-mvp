import React, { useEffect, useState } from "react";
import { readClassesByUserId } from "../../../services/classroom/ClassroomService.mjs";
import Button from "../../../components/Button";
import { useNavigate } from "react-router-dom"; // useNavigate 추가

const TeacherClassroomListPage = () => {
  const [classes, setClasses] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchClasses = async () => {
      const data = await readClassesByUserId();
      setClasses(data);
    };
    fetchClasses();
  }, []);

  const handleReadClass = (classId) => {
    navigate(`/classes/${classId}`);
  };

  return (
    <div>
      <h1>클래스 목록(강사)</h1>
      <p>강사가 개설예정, 수업중, 수업완료된 자신의 모든 클래스목록을 봄 </p>
      <p style={{ color: "white" }}>
        (강사 학생 따로 클래스룸 목록 페이지 구분되어있는데 통합가능?){" "}
      </p>
      <ul>
        {classes.map((classItem) => (
          <div>
            <li key={classItem.id}>{classItem.title}</li>{" "}
            <Button
              onClick={() => {
                handleReadClass(classItem.id);
              }}
            >
              입장
            </Button>
          </div>
        ))}
      </ul>
    </div>
  );
};
export default TeacherClassroomListPage;
