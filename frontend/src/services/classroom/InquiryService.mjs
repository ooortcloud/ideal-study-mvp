import apiClient from "../apiClient.mjs";

// 문의 등록
export const createInquiry = async (inquiryData) => {
  console.log("문의 등록 시도:", inquiryData);
  try {
    const response = await apiClient.post("/api/inquiries", inquiryData);
    console.log("문의 등록 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("문의 등록 실패:", err);
    throw err;
  }
};

// 문의 목록 조회 (클래스별)
export const getInquiriesByClassId = async (classId) => {
  console.log(`문의 목록 조회 (클래스별) 시도: classId=${classId}`);
  try {
    const response = await apiClient.get(`/inquiries/classes/${classId}`);
    console.log("문의 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("문의 목록 조회 실패:", err);

    // 페이지네이션을 포함한 가라데이터
    const mockData = {
      inquiries: Array.from({ length: 15 }, (_, index) => ({
        id: index + 1,
        title: `문의 제목 ${index + 1}`,
        author: ["홍길동", "김철수", "이영희", "박민수"][
          Math.floor(Math.random() * 4)
        ],
        content: `문의 내용 ${index + 1}입니다.`,
        isPublic: Math.random() > 0.5,
        comments: Math.floor(Math.random() * 5),
        createdAt: new Date(
          Date.now() - Math.random() * 10000000000
        ).toISOString(),
        authorId: Math.floor(Math.random() * 4) + 1,
      })),
      pagination: {
        currentPage: 1,
        totalPages: 3,
        totalItems: 15,
        itemsPerPage: 5,
      },
    };

    return mockData;
  }
};

// 문의 상세 조회
export const readInquiry = async (inquiryId) => {
  console.log(`문의 상세 조회 시도: inquiryId=${inquiryId}`);
  try {
    const response = await apiClient.get(`/inquiries/${inquiryId}`);
    console.log("문의 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("문의 상세 조회 실패:", err);

    // 상세 조회용 가라데이터
    const mockDetailData = {
      id: parseInt(inquiryId),
      title: "수업 일정 문의드립니다",
      content:
        "다음 주부터 시작되는 수업 일정에 대해 궁금한 점이 있습니다. 수업 시간 조정이 가능할까요?",
      author: "홍길동",
      authorId: 1,
      isPublic: true,
      comments: [
        {
          id: 1,
          content: "네, 가능합니다. 구체적으로 원하시는 시간을 말씀해 주세요.",
          author: "강사님",
          createdAt: "2024-03-15T09:00:00Z",
        },
        {
          id: 2,
          content: "감사합니다. 오후 3시로 변경 가능할까요?",
          author: "홍길동",
          createdAt: "2024-03-15T10:00:00Z",
        },
      ],
      createdAt: "2024-03-15T08:00:00Z",
      updatedAt: "2024-03-15T10:00:00Z",
    };

    return mockDetailData;
  }
};

// 문의 수정
export const updateInquiry = async (inquiryId, inquiryData) => {
  console.log(`문의 수정 시도: inquiryId=${inquiryId}`, inquiryData);
  try {
    const response = await apiClient.patch(
      `/api/inquiries/${inquiryId}`,
      inquiryData
    );
    console.log("문의 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("문의 수정 실패:", err);
    throw err;
  }
};

// 문의 삭제
export const deleteInquiry = async (inquiryId) => {
  console.log(`문의 삭제 시도: inquiryId=${inquiryId}`);
  try {
    const response = await apiClient.delete(`/api/inquiries/${inquiryId}`);
    console.log("문의 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("문의 삭제 실패:", err);
    throw err;
  }
};
