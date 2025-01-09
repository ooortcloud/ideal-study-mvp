import React, { useState, useEffect } from "react";
import { readAssignmentSubmissions } from "../../../services/classroom/assessment/SubmissionService.mjs";
import { readAssignmentFeedbacks } from "../../../services/classroom/assessment/FeedbackService.mjs";

const AssignmentManagementPage = () => {
  const [assignments, setAssignments] = useState();
  const [feedbacks, setFeedbacks] = useState();

  useEffect(() => {
    const fetchData = async () => {
      const assignmentsData = await readAssignmentSubmissions();
      const feedbacksData = await readAssignmentFeedbacks();
      setAssignments(assignmentsData);
      setFeedbacks(feedbacksData);
    };
    fetchData();
  }, []);

  return (
    <div>
      <h1>과제 관리</h1>
      <h3>과제목록</h3>
      {/* 과제 목록을 렌더링하는 부분 */}
      {assignments &&
        assignments.map((assignment) => (
          <div key={assignment.id}>
            <li>
              {assignment.title}
              &nbsp;
              {assignment.status === "제출" ? (
                <button>제출하기</button>
              ) : (
                "(완료)"
              )}
            </li>
          </div>
        ))}
      <h3>피드백목록</h3>
      {/* 피드백 목록을 렌더링하는 부분 */}
      {feedbacks &&
        feedbacks.map((feedback) => (
          <div key={feedback.id}>
            <li>{feedback.comment}</li>
          </div>
        ))}
    </div>
  );
};

export default AssignmentManagementPage;
