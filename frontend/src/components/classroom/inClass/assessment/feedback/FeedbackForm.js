import React, { useState } from "react";

const FeedbackForm = ({ submissionId, onSubmit }) => {
  const [feedback, setFeedback] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(submissionId, feedback);
    setFeedback("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>평가 피드백 폼</h3>
      <textarea
        placeholder="피드백 입력"
        value={feedback}
        onChange={(e) => setFeedback(e.target.value)}
        required
      />
      <button type="submit">제출</button>
    </form>
  );
};

export default FeedbackForm;
