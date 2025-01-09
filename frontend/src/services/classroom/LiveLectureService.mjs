import apiClient from "../apiClient.mjs";

// 실시간 특강 생성
export const createLiveLecture = async (lectureData) => {
  console.log("실시간 특강 생성 시도:", lectureData);
  try {
    const response = await apiClient.post("/api/live-lectures", lectureData);
    console.log("실시간 특강 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("실시간 특강 생성 실패:", err);
    return {
      ...lectureData,
      id: Math.random().toString(36).substr(2, 9),
      status: "SCHEDULED",
      createdAt: new Date().toISOString(),
    };
  }
};

// 실시간 특강 조회
export const readLiveLectureById = async (lectureId) => {
  console.log("실시간 특강 조회 시도:", lectureId);
  try {
    const response = await apiClient.get(`/live-lectures/${lectureId}`);
    console.log("실시간 특강 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("실시간 특강 조회 실패:", err);
    return {
      id: lectureId,
      title: "샘플 특강",
      description: "임시 특강입니다",
      startTime: new Date().toISOString(),
      endTime: new Date(Date.now() + 3600000).toISOString(),
      platform: "ZOOM",
      isOnline: true,
      link: "https://zoom.us/sample",
      status: "SCHEDULED",
    };
  }
};

// 실시간 특강 목록 조회
export const getLiveLecturesByClassId = async (classId) => {
  console.log("실시간 특강 목록 조회 시도:", classId);
  try {
    const response = await apiClient.get(`/live-lectures/list/${classId}`);
    console.log("실시간 특강 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("실시간 특강 목록 조회 실패:", err);
    return Array(5)
      .fill()
      .map((_, i) => ({
        id: `dummy-lecture-${i}`,
        title: `샘플 특강 ${i + 1}`,
        description: `샘플 특강 설명 ${i + 1}`,
        startTime: new Date(Date.now() + i * 86400000).toISOString(),
        endTime: new Date(Date.now() + i * 86400000 + 3600000).toISOString(),
        platform: ["ZOOM", "GOOGLE_MEET", "DISCORD"][i % 3],
        isOnline: true,
        link: "https://zoom.us/sample",
        status: "SCHEDULED",
      }));
  }
};

// 실시간 특강 수정
export const updateLiveLecture = async (lectureData) => {
  console.log("실시간 특강 수정 시도:", lectureData);
  try {
    if (!lectureData.classId) {
      throw new Error("classroomId is required");
    }
    const response = await apiClient.patch("/api/live-lectures", lectureData);
    console.log("실시간 특강 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("실시간 특강 수정 실패:", err);
    return {
      ...lectureData,
      updateDate: new Date().toISOString(),
    };
  }
};

// 실시간 특강 삭제
export const deleteLiveLecture = async (lectureId, classroomId) => {
  console.log("실시간 특강 삭제 시도:", { lectureId, classroomId });
  try {
    await apiClient.delete("/api/live-lectures", {
      data: { id: lectureId, classroomId },
    });
    console.log("실시간 특강 삭제 성공");
    return true;
  } catch (err) {
    console.error("실시간 특강 삭제 실패:", err);
    return false;
  }
};
