import React, { useEffect, useState } from "react";
import { readExams } from "../../../services/classroom/assessment/AssessmentService.mjs";

const ExamListPage = () => {
  const [exams, setExams] = useState([]);

  useEffect(() => {
    const fetchExams = async () => {
      const data = await readExams();
      setExams(data);
    };
    fetchExams();
  }, []);

  return (
    <div>
      <h3>시험 목록</h3>
      <p>
        강사가 자신이 작성한 모든 시험에 대한 목록을 봄 (클래스 선택 -
        제출목록조회로 생각){" "}
      </p>
      <ul>
        {exams.map((exam) => (
          <li key={exam.id}>{exam.title}</li>
        ))}
      </ul>
    </div>
  );
};

export default ExamListPage;
