import apiClient from './apiClient.mjs';

/**
 * 클래스 생성
 */
export const createClass = async (data) => {
  console.log("클래스 생성 API 시도:", data);
  try {
    const response = await apiClient.post('/api/classes', data);
    console.log("클래스 생성 API 성공:", response);
    return response.data;
  } catch (error) {
    console.error('클래스 생성 API 실패:', error);
    return { id: 'dummy-id', title: '더미 클래스', description: 'API 실패로 생성된 더미 데이터' }; // 더미 데이터 반환
  }
};

/**
 * 클래스 목록 조회
 */
export const readClasses = async () => {
  console.log("클래스 목록 조회 API 시도");
  try {
    const response = await apiClient.get('/api/classes');
    console.log("클래스 목록 조회 API 성공:", response);
    return response.data;
  } catch (error) {
    console.error('클래스 목록 조회 API 실패:', error);
    return [
      { id: 'dummy-1', title: '한석원의 60일 지옥 부트캠프', description: '하루 18시간 공부할 학생들만 참여가능' },
      { id: 'dummy-2', title: '현우진의 노베이스 부트캠프', description: '인생 조진거같다 싶으면 드루와 갱생시켜줌' },
    ]; // 더미 데이터 반환
  }
};

/**
 * 클래스 상세 조회
 */
export const readClassById = async (classId) => {
  console.log("클래스 상세 조회 API 시도:", classId);
  try {
    const response = await apiClient.get(`/api/classes/${classId}`);
    console.log("클래스 상세 조회 API 성공:", response);
    return response.data;
  } catch (error) {
    console.error('클래스 상세 조회 API 실패:', error);
    return {
      id: classId,
      title: `한석원의 60일 지옥의 수학 부트캠프`,
      description: '9등급도 60일만에 1등급을 만들어주는 수업',
      thumbnail: 'temp'
    }; // 더미 데이터 반환
  }
};

/**
 * 클래스 수정
 */
export const updateClass = async (classId, data) => {
  console.log("클래스 수정 API 시도:", classId, data);
  try {
    const response = await apiClient.patch(`/api/classes/${classId}`, data);
    console.log("클래스 수정 API 성공:", response);
    return response.data;
  } catch (error) {
    console.error('클래스 수정 API 실패:', error);
    return {
      id: classId,
      ...data,
      title: data.title || '수정된 더미 클래스',
      description: data.description || 'API 실패로 수정된 더미 데이터',
    }; // 수정된 더미 데이터 반환
  }
};

/**
 * 클래스 삭제
 */
export const deleteClass = async (classId) => {
  console.log("클래스 삭제 API 시도:", classId);
  try {
    const response = await apiClient.delete(`/api/classes/${classId}`);
    console.log("클래스 삭제 API 성공:", response);
    return true; // 성공 여부 반환
  } catch (error) {
    console.error('클래스 삭제 API 실패:', error);
    console.warn(`더미 데이터: 클래스 ${classId} 삭제 실패로 처리`);
    return false; // 실패 시 false 반환
  }
};
