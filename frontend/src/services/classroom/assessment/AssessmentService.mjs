import apiClient from "../../apiClient.mjs";

// 강사 과제 생성
export const createAssignment = async (assignmentData) => {
  console.log("과제 생성 시도:", assignmentData);
  try {
    const response = await apiClient.post("/api/assignments", assignmentData);
    console.log("과제 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 생성 실패:", err);
    throw err;
  }
};

// 강사 과제 목록 조회
export const readAssignments = async () => {
  console.log("과제 목록 조회 시도");
  try {
    const response = await apiClient.get("/api/assignments");
    console.log("과제 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 목록 조회 실패:", err);

    return [
      {
        students: ["홍길동", "김철수", "이영희"], // 학생 이름 배열
        evaluationId: "eval123", // 평가 ID
        title: "수학 과제", // 제목
        description: "이 과제는 수학 문제를 포함합니다.", // 설명
        startTime: "2023-10-01T10:00:00Z", // 시작 시간
        endTime: "2023-10-15T23:59:59Z", // 종료 시간
        examTime: "2시간", // 시험 시간
        evaluationText: "이 과제는 학생들의 수학 능력을 평가합니다.", // 평가 텍스트
        evaluationUrl: "http://example.com/evaluation", // 평가 URL
        averageScore: 75, // 평균 점수
      },
      {
        students: ["김철수", "이영희"], // 학생 이름 배열
        evaluationId: "eval123", // 평가 ID
        title: "수학2 과제", // 제목
        description: "이 과제는 수학 문제를 포함합니다.", // 설명
        startTime: "2023-10-01T10:00:00Z", // 시작 시간
        endTime: "2023-10-15T23:59:59Z", // 종료 시간
        examTime: "2시간", // 시험 시간
        evaluationText: "이 과제는 학생들의 수학 능력을 평가합니다.", // 평가 텍스트
        evaluationUrl: "http://example.com/evaluation", // 평가 URL
        averageScore: 75, // 평균 점수
      },
    ];
  }
};

// 강사 과제 상세 조회
export const readAssignment = async (assignmentId) => {
  console.log(`과제 상세 조회 시도: assignmentId=${assignmentId}`);
  try {
    const response = await apiClient.get(`/api/assignments/${assignmentId}`);
    console.log("과제 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 상세 조회 실패:", err);
  }
};

// 강사 과제 수정
export const updateAssignment = async (assignmentId, assignmentData) => {
  console.log(`과제 수정 시도: assignmentId=${assignmentId}`, assignmentData);
  try {
    const response = await apiClient.patch(
      `/api/assignments/${assignmentId}`,
      assignmentData
    );
    console.log("과제 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 수정 실패:", err);
    throw err;
  }
};

// 강사 과제 삭제
export const deleteAssignment = async (assignmentId) => {
  console.log(`과제 삭제 시도: assignmentId=${assignmentId}`);
  try {
    const response = await apiClient.delete(`/api/assignments/${assignmentId}`);
    console.log("과제 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("과제 삭제 실패:", err);
    throw err;
  }
};

// 강사 시험 생성
export const createExam = async (examData) => {
  console.log("시험 생성 시도:", examData);
  try {
    const response = await apiClient.post("/api/exams", examData);
    console.log("시험 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 생성 실패:", err);
    throw err;
  }
};

// 강사 시험 목록 조회
export const readExams = async () => {
  console.log("시험 목록 조회 시도");
  try {
    const response = await apiClient.get("/api/exams");
    console.log("시험 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 목록 조회 실패:", err);
    return [
      {
        title: "수학 시험", // 시험 제목
        description: "이 시험은 수학 문제를 포함합니다.", // 설명
        startTime: "2023-10-01T10:00:00Z", // 시작 시간
        endTime: "2023-10-15T23:59:59Z", // 종료 시간
        duration: "2시간", // 시험 시간
        averageScore: 80, // 평균 점수
      },
      {
        title: "과학 시험", // 시험 제목
        description: "이 시험은 과학 문제를 포함합니다.", // 설명
        startTime: "2023-10-16T10:00:00Z", // 시작 시간
        endTime: "2023-10-30T23:59:59Z", // 종료 시간
        duration: "1시간 30분", // 시험 시간
        averageScore: 85, // 평균 점수
      },
    ];
  }
};

// 강사 시험 상세 조회
export const readExam = async (examId) => {
  console.log(`시험 상세 조회 시도: examId=${examId}`);
  try {
    const response = await apiClient.get(`/api/exams/${examId}`);
    console.log("시험 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 상세 조회 실패:", err);
    throw err;
  }
};

// 강사 시험 수정
export const updateExam = async (examId, examData) => {
  console.log(`시험 수정 시도: examId=${examId}`, examData);
  try {
    const response = await apiClient.patch(`/api/exams/${examId}`, examData);
    console.log("시험 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 수정 실패:", err);
    throw err;
  }
};

// 강사 시험 삭제
export const deleteExam = async (examId) => {
  console.log(`시험 삭제 시도: examId=${examId}`);
  try {
    const response = await apiClient.delete(`/api/exams/${examId}`);
    console.log("시험 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("시험 삭제 실패:", err);
    throw err;
  }
};
