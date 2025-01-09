import apiClient from "../apiClient.mjs";

// 수업 신청
export const createEnrollment = async (enrollmentData) => {
  console.log("수업 신청 시도:", enrollmentData);
  try {
    const response = await apiClient.post("/api/enrollments", enrollmentData);
    console.log("수업 신청 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("수업 신청 실패:", err);
  }
};

// 수업 신청 수정
export const updateEnrollment = async (enrollmentId, enrollmentData) => {
  console.log(
    `수업 신청 수정 시도: enrollmentId=${enrollmentId}`,
    enrollmentData
  );
  try {
    const response = await apiClient.patch(
      `/api/enrollments/${enrollmentId}`,
      enrollmentData
    );
    console.log("수업 신청 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("수업 신청 수정 실패:", err);
    throw err;
  }
};

// 수업 신청 취소
export const cancelEnrollment = async (enrollmentId) => {
  console.log(`수업 신청 취소 시도: enrollmentId=${enrollmentId}`);
  try {
    const response = await apiClient.delete(`/api/enrollments/${enrollmentId}`);
    console.log("수업 신청 취소 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("수업 신청 취소 실패:", err);
    throw err;
  }
};

// 수업 신청 목록 조회 (클래스별) - 일단 10개씩 받음
export const readEnrollmentsByClassId = async (classId) => {
  console.log(`수업 신청 목록 조회 (클래스별) 시도: classId=${classId}`);
  try {
    const response = await apiClient.get(
      `/api/enrollments/classes/${classId}?page=10`
    );
    console.log("수업 신청 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("수업 신청 목록 조회 실패:", err);
    return { dtoList: [] };
  }
};

// 수업 신청 목록 조회 (유저별)
export const readEnrollmentsByUserId = async (userId) => {
  console.log(`수업 신청 목록 조회 (유저별) 시도: userId=${userId}`);
  try {
    const response = await apiClient.get(`/api/enrollments/users`);
    console.log("수업 신청 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("수업 신청 목록 조회 실패:", err);
    throw err;
  }
};

// 수업 신청 상세 조회 (userId, classId 로 검색해야됨)
export const readEnrollment = async (enrollmentId) => {
  console.log(`수업 신청 상세 조회 시도: enrollmentId=${enrollmentId}`);
  try {
    const response = await apiClient.get(`/api/enrollments/${enrollmentId}`);
    console.log("수업 신청 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("수업 신청 상세 조회 실패:", err);

    // 실패 시 기본 형식에 맞는 더미 데이터 반환
    return {};
  }
};
