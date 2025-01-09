import React from "react";

const AssessmentList = ({ assignments, onEdit, onDelete }) => {
  return (
    <div>
      <h3>평가 목록</h3>
      <ul>
        {assignments.map((assignment) => (
          <li key={assignment.id}>
            {assignment.title}
            <button onClick={() => onEdit(assignment.id)}>수정</button>
            <button onClick={() => onDelete(assignment.id)}>삭제</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AssessmentList;
