import React from "react";

const SubmissionDetail = ({ submission }) => {
  return (
    <div>
      <h3>제출 상세</h3>
      <p>학생 ID: {submission.studentId}</p>
      <p>제출 내용: {submission.content}</p>
    </div>
  );
};

export default SubmissionDetail;
