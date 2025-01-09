import apiClient from "../apiClient.mjs";

// 인강 생성
export const createRecordedLecture = async (lectureData) => {
  console.log("인강 생성 시도:", lectureData);
  try {
    const response = await apiClient.post(
      "/api/recorded-lectures",
      lectureData
    );
    console.log("인강 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("인강 생성 실패:", err);
  }
};

// 인강 목록 조회
export const readRecordedLecturesByClassId = async (classId) => {
  console.log(`인강 목록 조회 시도!!: classId=${classId}`);
  try {
    const response = await apiClient.get(
      `/api/recorded-lectures/${classId}`
    );
    console.log("인강 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("인강 목록 조회 실패:", err);

    // 가라데이터
    const mockData = {
      data: Array.from({ length: 3 }, (_, index) => ({
        id: index + 1,
        order: index + 1,
        title: `${index + 1}강: JavaScript 기초 문법`,
        description: `JavaScript의 기본적인 문법과 활용 방법을 배웁니다. (${
          index + 1
        }차시)`,
        duration: Math.floor(Math.random() * 40 + 20) + ":00", // 20~60분 사이
        videoUrl: `https://vimeo.com/${1037702745 + index}`,
        createdAt: new Date(
          Date.now() - Math.random() * 10000000000
        ).toISOString(),
        updatedAt: new Date(
          Date.now() - Math.random() * 5000000000
        ).toISOString(),
      })),
      pagination: {
        currentPage: 1,
        totalPages: 2,
        totalItems: 10,
        itemsPerPage: 5,
      },
    };
    return mockData;
  }
};

// 인강 상세 조회
export const readRecordedLecture = async (lectureId) => {
  console.log(`인강 상세 조회 시도: lectureId=${lectureId}`);
  try {
    const response = await apiClient.get(`/api/recorded-lectures/${lectureId}`);
    console.log("인강 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("인강 상세 조회 실패:", err);

    // 가라데이터
    return {
      id: lectureId,
      order: 1,
      title: "1강: JavaScript 기초 문법",
      description: "JavaScript의 기본적인 문법과 활용 방법을 배웁니다.",
      duration: "45:00",
      videoUrl: "https://vimeo.com/1037702745",
      createdAt: "2024-03-15T08:00:00Z",
      updatedAt: "2024-03-15T08:00:00Z",
    };
  }
};

// 인강 수정
export const updateRecordedLecture = async (lectureId, lectureData) => {
  console.log(`인강 수정 시도: lectureId=${lectureId}`, lectureData);
  try {
    const response = await apiClient.patch(
      `/api/recorded-lectures/${lectureId}`,
      lectureData
    );
    console.log("인강 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("인강 수정 실패:", err);
    throw err;
  }
};

// 인강 삭제
export const deleteRecordedLecture = async (lectureId) => {
  console.log(`인강 삭제 시도: lectureId=${lectureId}`);
  try {
    const response = await apiClient.delete(
      `/api/recorded-lectures/${lectureId}`
    );
    console.log("인강 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("인강 삭제 실패:", err);
    throw err;
  }
};
