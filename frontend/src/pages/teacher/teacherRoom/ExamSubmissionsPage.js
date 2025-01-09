import React, { useEffect, useState } from "react";
import { readExamSubmissions } from "../../../services/classroom/assessment/SubmissionService.mjs";

const ExamSubmissionsPage = ({ examId }) => {
  const [submissions, setSubmissions] = useState([]);
  useEffect(() => {
    const fetchSubmissions = async () => {
      const data = await readExamSubmissions(examId);
      setSubmissions(data);
    };
    fetchSubmissions();
  }, [examId]);
  return (
    <div>
      <h3>시험 제출 목록</h3>
      <p>
        자신이 작성한 모든 시험에 대한 학생들 제출 목록을 봄 (클래스 선택 -
        제출목록조회로 생각){" "}
      </p>
      <ul>
        {submissions.map((submission) => (
          <li key={submission.id}>
            학생 ID: {submission.studentId} - 피드백:{" "}
            {submission.feedback ? "있음" : "없음"}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default ExamSubmissionsPage;
