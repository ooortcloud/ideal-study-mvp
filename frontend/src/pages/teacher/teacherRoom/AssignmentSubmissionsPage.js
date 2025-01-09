import React, { useEffect, useState } from "react";
import { readAssignmentSubmissions } from "../../../services/classroom/assessment/SubmissionService.mjs";
const AssignmentSubmissionsPage = ({ assignmentId }) => {
  const [submissions, setSubmissions] = useState([]);
  useEffect(() => {
    const fetchSubmissions = async () => {
      const data = await readAssignmentSubmissions(assignmentId);
      setSubmissions(data);
    };
    fetchSubmissions();
  }, [assignmentId]);
  return (
    <div>
      <h3>과제 제출 목록</h3>
      <p>
        자신이 작성한 모든 과제에 대한 학생들 제출 목록을 봄 (클래스 선택 -
        제출목록조회로 생각){" "}
      </p>
      <ul>
        {submissions.map((submission) => (
          <li key={submission.id}>
            학생 ID: {submission.id} - 상태: {submission.status}
            <button>피드백: {submission.feedback ? "있음" : "없음"}</button>
          </li>
        ))}
      </ul>
    </div>
  );
};
export default AssignmentSubmissionsPage;
