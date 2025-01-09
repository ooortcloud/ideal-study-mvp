import React, { useEffect, useState } from "react";
import { readClassesByUserId } from "../../../services/classroom/ClassroomService.mjs";
import Button from "../../../components/Button";

const StudentClassroomListPage = () => {
  const [classes, setClasses] = useState([]);
  useEffect(() => {
    const fetchClasses = async () => {
      const data = await readClassesByUserId();
      setClasses(data);
    };
    fetchClasses();
  }, []);
  return (
    <div>
      <h1>클래스 목록(학생)</h1>
      <p>강사가 개설예정, 수업중, 수업완료된 자신의 모든 클래스 목록을 봄</p>
      <p style={{ color: "white" }}>
        (강사 학생 따로 클래스룸 목록 페이지 구분되어있는데 통합가능?){" "}
      </p>
      <ul>
        {classes.map((classItem) => (
          <li key={classItem.id}>
            {classItem.title} <button>입장</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default StudentClassroomListPage;
