import React, { useState, useEffect } from "react";
import { readExamSubmissions } from "../../../services/classroom/assessment/SubmissionService.mjs";
import { readExamFeedbacks } from "../../../services/classroom/assessment/FeedbackService.mjs";

const ExamManagementPage = () => {
  const [exams, setExams] = useState();
  const [feedbacks, setFeedbacks] = useState();

  useEffect(() => {
    const fetchData = async () => {
      const data = await readExamSubmissions();
      const feedbacksData = await readExamFeedbacks();
      setExams(data);
      setFeedbacks(feedbacksData);
    };
    fetchData();
  }, []);

  return (
    <div>
      <h1>시험 관리</h1>
      <h3>시험목록</h3>
      {/* 과제 목록을 렌더링하는 부분 */}
      {exams &&
        exams.map((exam) => (
          <div key={exam.id}>
            <li>
              {exam.title}
              &nbsp;
              {exam.status === "제출" ? <button>제출하기</button> : "(완료)"}
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

export default ExamManagementPage;
