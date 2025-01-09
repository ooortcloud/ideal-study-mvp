import React from "react";

const SubmissionList = ({ submissions, onGrade, onFeedback }) => {
  return (
    <div>
      <h3>과제 제출 목록</h3>
      <ul>
        {submissions.map((submission) => (
          <li key={submission.id}>
            <p>학생 ID: {submission.studentId}</p>
            <button onClick={() => onGrade(submission.id)}>점수 등록</button>
            <button onClick={() => onFeedback(submission.id)}>
              피드백 등록
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SubmissionList;
