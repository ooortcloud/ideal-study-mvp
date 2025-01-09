import apiClient from "../apiClient.mjs";

// 출석
export const checkInAttendance = async (userId) => {
  console.log("출석 체크인 시도:", userId);
  try {
    const response = await apiClient.post(
      `/api/attendance/check-in/${userId}`,
      null,
      {
        params: {
          year: new Date().getFullYear(),
          month: new Date().getMonth() + 1,
        },
      }
    );
    console.log("출석 체크인 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("출석 체크인 실패:", err);
    return null; // 실패 시 null 반환
  }
};

// 퇴실
export const checkOutAttendance = async (userId) => {
  console.log("퇴실 체크아웃 시도:", userId);
  try {
    const response = await apiClient.post(
      `/api/attendance/check-out/${userId}`,
      null,
      {
        params: {
          year: new Date().getFullYear(),
          month: new Date().getMonth() + 1,
        },
      }
    );
    console.log("퇴실 체크아웃 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("퇴실 체크아웃 실패:", err);
    return null; // 실패 시 null 반환
  }
};

// 출석 조회 (강사용)
export const readClassAttendance = async (classId, year, month) => {
  console.log("출석 조회 시도:", { classId, year, month });
  try {
    const response = await apiClient.get(`/api/attendance/teacher/${classId}`, {
      params: { year, month },
    });
    console.log("출석 조회 성공:", response.data);
    return response;
  } catch (err) {
    console.error("출석 조회 실패:", err);
    return [{ userId: "얼음왕비", date: new Date().toISOString() }]; // 실패 시 빈 배열 반환
  }
};

// 일자별 출석 조회
export const readUserAttendance = async (classId, date) => {
  console.log("일자별 출석 조회 시도:", { classId, date });
  try {
    const response = await apiClient.get(`/api/attendance/${classId}/${date}`);
    console.log("일자별 출석 조회 성공:", response.data);
    return response;
  } catch (err) {
    console.error("일자별 출석 조회 실패:", err);
    return [{ userId: "얼음공주", date: new Date().toISOString() }]; // 실패 시 빈 배열 반환
  }
};
