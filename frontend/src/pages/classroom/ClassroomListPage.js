import React, { useEffect, useState } from 'react';
import ClassroomCard from '../../components/classroom/ClassroomCard';
import { readClasses } from '../../services/ClassroomService.mjs';

import './ClassroomListPage.css';

const ClassroomList = () => {
  const [classrooms, setClassrooms] = useState([]);

  useEffect(() => {
    // API 호출로 클래스 목록 가져오기
    const fetchClasses = async () => {
      const data = await readClasses();
      setClassrooms(data);
    };
    fetchClasses();
  }, []);

  return (
    <div>
      <h1>클래스 목록</h1>
      <div className="classroom-grid">
        {classrooms.map((classroom) => (
          <ClassroomCard key={classroom.id} classroom={classroom} />
        ))}
      </div>
    </div>
  );
};

export default ClassroomList;
