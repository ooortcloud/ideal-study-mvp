import React, { useState } from "react";

const AssessmentForm = ({ onCreate }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onCreate({ title, description });
    setTitle("");
    setDescription("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>평가 생성</h3>
      <input
        type="text"
        placeholder="과제 제목"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <textarea
        placeholder="과제 설명"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      />
      <button type="submit">생성</button>
    </form>
  );
};

export default AssessmentForm;
