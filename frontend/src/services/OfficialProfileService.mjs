import apiClient from './apiClient.mjs';

/**
 * 공식프로필 조회
 */
export const ReadOfficialProfile = async (userId) => {
  console.log("공식프로필 조회 API 시도:", userId);
    try {
      const response = await apiClient.get('/api/officialProfile/{userId}');
      console.log("공식프로필 조회 API 성공:", response);
      return response.data;
    } catch (error) {
      console.error('공식프로필조회 API 실패:', error);
  
      // 임시로 더미 HTML 데이터 반환
      const dummyData = {
        content: `
            <p><strong>안녕하세요,</strong> <em>여러분</em>ㅎㅎ!</p>
            <p>저는 <span style="color: blue;">일타</span> 강사 한석원입니다.</p>
        `
        };
  
      return dummyData;
    }
  };

/**
 * 공식프로필 수정
 */
export const UpdateOfficialProfile = async ({content}) => {
    console.log("공식프로필 수정 API 시도:", content);
    try{
      const response = await apiClient.post('/api/officialProfile/{userId}', {
        content
      });
      console.log("공식프로필 수정 API 성공:", response);
      if (response.ok) {
        alert('내용이 제출되었습니다!');
      } else {
        alert('제출 실패');
      }

    }catch(error){
      console.log("공식프로필 수정 API 실패:", error);
      console.log("일단 수정완료");
    }
};