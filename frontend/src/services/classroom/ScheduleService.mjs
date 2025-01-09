import apiClient from '../apiClient.mjs';

// 스케줄러 일정 생성 API
export const createSchedule = async (classId, scheduleData) => {
    console.log("스케줄러 일정 생성 API 시도:", { classId, scheduleData });
    try {
      const response = await apiClient.post(`/api/classes/${classId}/schedules`, scheduleData);
      console.log("스케줄러 일정 생성 API 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("스케줄러 일정 생성 API 실패", err);
      alert("스케쥴러 일정 생성이 되었을까요?");
    }
  };
  

// 스케줄러 목록 조회 API (클래스별)
export const readSchedulesByClass = async (classId) => {
    console.log("스케줄러 목록 조회 API 시도:", { classId });
    try {
      const response = await apiClient.get(`/api/schedules/classes/${classId}`);
      console.log("스케줄러 목록 조회 API 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("스케줄러 목록 조회 API 실패", err);
      
      const data = [
        {
          id: 1,
          date: new Date(2024, 11, 1), // 12월 1일
          title: "장보기",
          author: "홍길동",
          isPublic: true,
          comments: 3,
          content: "이번 주말에 마트에서 필요한 생필품을 구입해야 합니다.",
        },
        {
          id: 2,
          date: new Date(2024, 11, 5), // 12월 5일
          title: "수업준비",
          author: "김철수",
          isPublic: false,
          comments: 1,
          content: "다음 주 발표 자료와 강의 준비를 완료해야 합니다.",
        },
        {
          id: 3,
          date: new Date(2024, 11, 10), // 12월 10일
          title: "도함수의 활용(1) 쪽지시험",
          author: "이영희",
          isPublic: true,
          comments: 0, 
          content: "성적 80점 미만인 학생은 강제 보충학습입니다",
        },
        {
          id: 4,
          date: new Date(2024, 11, 20), // 12월 20일
          title: "2천만원 결제",
          author: "박민수",
          isPublic: false,
          comments: 2,
          content: "회사 운영비로 2천만 원을 지출해야 합니다.",
        },
      ];
       

      return data;
    }
  };

// 스케줄 상세 조회
export const readSchedule = async (scheduleId) => {
  console.log(`스케줄 상세 조회 시도: scheduleId=${scheduleId}`);
  try {
    const response = await apiClient.get(`/api/schedules/${scheduleId}`);
    console.log("스케줄 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄 상세 조회 실패:", err);
    throw err;
  }
};

// 스케줄 수정
export const updateSchedule = async (scheduleId, scheduleData) => {
  console.log(`스케줄 수정 시도: scheduleId=${scheduleId}`, scheduleData);
  try {
    const response = await apiClient.patch(`/api/schedules/${scheduleId}`, scheduleData);
    console.log("스케줄 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄 수정 실패:", err);
    throw err;
  }
};

// 스케줄 삭제
export const deleteSchedule = async (scheduleId) => {
  console.log(`스케줄 삭제 시도: scheduleId=${scheduleId}`);
  try {
    const response = await apiClient.delete(`/api/schedules/${scheduleId}`);
    console.log("스케줄 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄 삭제 실패:", err);
    throw err;
  }
};