import React, { useState } from "react";

const SubmissionForm = ({ assignmentId, onSubmit }) => {
  const [submission, setSubmission] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(assignmentId, submission);
    setSubmission("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>평가 제출</h3>
      <textarea
        placeholder="답안 입력"
        value={submission}
        onChange={(e) => setSubmission(e.target.value)}
        required
      />
      <button type="submit">제출</button>
    </form>
  );
};

export default SubmissionForm;
