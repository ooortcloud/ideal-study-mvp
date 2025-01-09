import apiClient from "../../apiClient.mjs";

// 과제 피드백 생성
export const createAssignmentFeedback = async (assignmentId, feedbackData) => {
  console.log("과제 피드백 생성 시도:", feedbackData);
  try {
    const response = await apiClient.post(
      `/api/assignments/${assignmentId}/feedback`,
      feedbackData
    );
    console.log("과제 피드백 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 피드백 생성 실패:", err);
    throw err;
  }
};

// 과제 피드백 목록 조회
export const readAssignmentFeedbacks = async (assignmentId) => {
  console.log(`과제 피드백 목록 조회 시도: assignmentId=${assignmentId}`);
  try {
    const response = await apiClient.get(
      `/api/assignments/${assignmentId}/feedback`
    );
    console.log("과제 피드백 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 피드백 목록 조회 실패:", err);
    return [{ id: 0, assignmentId: 0, comment: "잘했어요" }]; // 가라데이터 반환
  }
};

// 과제 피드백 상세 조회
export const readAssignmentFeedback = async (feedbackId) => {
  console.log(`과제 피드백 상세 조회 시도: feedbackId=${feedbackId}`);
  try {
    const response = await apiClient.get(`/api/feedback/${feedbackId}`);
    console.log("과제 피드백 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 피드백 상세 조회 실패:", err);
    throw err;
  }
};

// 과제 피드백 수정
export const updateAssignmentFeedback = async (feedbackId, feedbackData) => {
  console.log(`과제 피드백 수정 시도: feedbackId=${feedbackId}`, feedbackData);
  try {
    const response = await apiClient.patch(
      `/api/feedback/${feedbackId}`,
      feedbackData
    );
    console.log("과제 피드백 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 피드백 수정 실패:", err);
    throw err;
  }
};

// 과제 피드백 삭제
export const deleteAssignmentFeedback = async (feedbackId) => {
  console.log(`과제 피드백 삭제 시도: feedbackId=${feedbackId}`);
  try {
    const response = await apiClient.delete(`/api/feedback/${feedbackId}`);
    console.log("과제 피드백 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 피드백 삭제 실패:", err);
    throw err;
  }
};

// 시험 피드백 생성
export const createExamFeedback = async (examId, feedbackData) => {
  console.log("시험 피드백 생성 시도:", feedbackData);
  try {
    const response = await apiClient.post(
      `/api/exams/${examId}/feedback`,
      feedbackData
    );
    console.log("시험 피드백 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 피드백 생성 실패:", err);
    throw err;
  }
};

// 시험 피드백 목록 조회
export const readExamFeedbacks = async (examId) => {
  console.log(`시험 피드백 목록 조회 시도: examId=${examId}`);
  try {
    const response = await apiClient.get(`/api/exams/${examId}/feedback`);
    console.log("시험 피드백 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 피드백 목록 조회 실패:", err);
    return [{ id: 0, examId: 0, comment: "잘했어요" }]; // 가라데이터 반환
  }
};

// 시험 피드백 상세 조회
export const readExamFeedback = async (feedbackId) => {
  console.log(`시험 피드백 상세 조회 시도: feedbackId=${feedbackId}`);
  try {
    const response = await apiClient.get(`/api/feedback/${feedbackId}`);
    console.log("시험 피드백 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 피드백 상세 조회 실패:", err);
    throw err;
  }
};

// 시험 피드백 수정
export const updateExamFeedback = async (feedbackId, feedbackData) => {
  console.log(`시험 피드백 수정 시도: feedbackId=${feedbackId}`, feedbackData);
  try {
    const response = await apiClient.patch(
      `/api/feedback/${feedbackId}`,
      feedbackData
    );
    console.log("시험 피드백 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 피드백 수정 실패:", err);
    throw err;
  }
};

// 시험 피드백 삭제
export const deleteExamFeedback = async (feedbackId) => {
  console.log(`시험 피드백 삭제 시도: feedbackId=${feedbackId}`);
  try {
    const response = await apiClient.delete(`/api/feedback/${feedbackId}`);
    console.log("시험 피드백 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 피드백 삭제 실패:", err);
    throw err;
  }
};
