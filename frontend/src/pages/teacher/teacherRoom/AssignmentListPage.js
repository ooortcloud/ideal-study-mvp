import React, { useEffect, useState } from "react";
import { readAssignments } from "../../../services/classroom/assessment/AssessmentService.mjs";

const AssignmentListPage = () => {
  const [assignments, setAssignments] = useState([]);
  useEffect(() => {
    const fetchAssignments = async () => {
      const data = await readAssignments();
      setAssignments(data);
    };
    fetchAssignments();
  }, []);
  return (
    <div>
      <h3>과제 목록</h3>
      <p>
        강사가 자신이 작성한 모든 과제에 대한 목록을 봄 (클래스 선택 -
        제출목록조회로 생각){" "}
      </p>
      <ul>
        {assignments.map((assignment) => (
          <li key={assignment.id}>
            {assignment.title} by{" "}
            <button>{assignment.students.join(", ")}</button>
          </li>
        ))}
      </ul>
    </div>
  );
};
export default AssignmentListPage;
