import apiClient from "../../apiClient.mjs";

// 과제 제출 생성
export const createAssignmentSubmission = async (
  assignmentId,
  submissionData
) => {
  console.log("과제 제출 생성 시도:", submissionData);
  try {
    const response = await apiClient.post(
      `/api/assignments/${assignmentId}/submissions`,
      submissionData
    );
    console.log("과제 제출 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 제출 생성 실패:", err);
    throw err;
  }
};

// 과제 제출 목록 조회
export const readAssignmentSubmissions = async (assignmentId) => {
  console.log(`과제 제출 목록 조회 시도: assignmentId=${assignmentId}`);
  try {
    const response = await apiClient.get(
      `/api/assignments/${assignmentId}/submissions`
    );
    console.log("과제 제출 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 제출 목록 조회 실패:", err);
    return [
      { id: 1, title: "수학 20문제 풀기", status: "완료" },
      { id: 2, title: "영어 교과서 정리하기", status: "제출" },
      { id: 3, title: "영어 지문해석하기", status: "제출" },
    ]; // 기본 데이터로 샘플 제출 목록 반환
  }
};

// 과제 제출 상세 조회
export const readAssignmentSubmission = async (submissionId) => {
  console.log(`과제 제출 상세 조회 시도: submissionId=${submissionId}`);
  try {
    const response = await apiClient.get(`/api/submissions/${submissionId}`);
    console.log("과제 제출 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 제출 상세 조회 실패:", err);
    throw err;
  }
};

// 과제 제출 수정
export const updateAssignmentSubmission = async (
  submissionId,
  submissionData
) => {
  console.log(
    `과제 제출 수정 시도: submissionId=${submissionId}`,
    submissionData
  );
  try {
    const response = await apiClient.patch(
      `/api/submissions/${submissionId}`,
      submissionData
    );
    console.log("과제 제출 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 제출 수정 실패:", err);
    throw err;
  }
};

// 과제 제출 삭제
export const deleteAssignmentSubmission = async (submissionId) => {
  console.log(`과제 제출 삭제 시도: submissionId=${submissionId}`);
  try {
    const response = await apiClient.delete(`/api/submissions/${submissionId}`);
    console.log("과제 제출 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 제출 삭제 실패:", err);
    throw err;
  }
};

// 시험 제출 생성
export const createExamSubmission = async (examId, submissionData) => {
  console.log("시험 제출 생성 시도:", submissionData);
  try {
    const response = await apiClient.post(
      `/api/exams/${examId}/submissions`,
      submissionData
    );
    console.log("시험 제출 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 제출 생성 실패:", err);
    throw err;
  }
};

// 시험 제출 목록 조회
export const readExamSubmissions = async (examId) => {
  console.log(`시험 제출 목록 조회 시도: examId=${examId}`);
  try {
    const response = await apiClient.get(`/api/exams/${examId}/submissions`);
    console.log("시험 제출 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 제출 목록 조회 실패:", err);
    return [
      { id: 1, title: "샘플 시험 제출 1", status: "완료" },
      { id: 2, title: "샘플 시험 제출 2", status: "제출 중" },
      { id: 3, title: "샘플 시험 제출 3", status: "대기 중" },
    ]; // 기본 데이터로 샘플 시험 제출 목록 반환
  }
};

// 시험 제출 상세 조회
export const readExamSubmission = async (submissionId) => {
  console.log(`시험 제출 상세 조회 시도: submissionId=${submissionId}`);
  try {
    const response = await apiClient.get(`/api/submissions/${submissionId}`);
    console.log("시험 제출 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 제출 상세 조회 실패:", err);
    throw err;
  }
};

// 시험 제출 수정
export const updateExamSubmission = async (submissionId, submissionData) => {
  console.log(
    `시험 제출 수정 시도: submissionId=${submissionId}`,
    submissionData
  );
  try {
    const response = await apiClient.patch(
      `/api/submissions/${submissionId}`,
      submissionData
    );
    console.log("시험 제출 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 제출 수정 실패:", err);
    throw err;
  }
};

// 시험 제출 삭제
export const deleteExamSubmission = async (submissionId) => {
  console.log(`시험 제출 삭제 시도: submissionId=${submissionId}`);
  try {
    const response = await apiClient.delete(`/api/submissions/${submissionId}`);
    console.log("시험 제출 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 제출 삭제 실패:", err);
    throw err;
  }
};
