import apiClient from '../apiClient.mjs';

// 게시글 등록
export const createPost = async (postData) => {
    console.log("게시글 등록 시도:", postData);
    try {
      const response = await apiClient.post('/api/posts', postData);
      console.log("게시글 등록 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("게시글 등록 실패:", err);
      throw err;
    }
  };
  
// 게시글 목록 조회 (클래스별)
export const readPostsByClassId = async (classId) => {
    console.log(`게시글 목록 조회 (클래스별) 시도: classId=${classId}`);
    try {
      const response = await apiClient.get(`/api/posts/classes/${classId}`);
      console.log("게시글 목록 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("게시글 목록 조회 실패:", err);
      throw err;
    }
  };
  
// 게시글 목록 조회 (유저별)
export const readPostsByUserId = async (userId) => {
    console.log(`게시글 목록 조회 (유저별) 시도: userId=${userId}`);
    try {
      const response = await apiClient.get(`/api/posts/users/${userId}`);
      console.log("게시글 목록 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("게시글 목록 조회 실패:", err);
      throw err;
    }
  };
  
// 게시글 상세 조회
export const readPost = async (postId) => {
    console.log(`게시글 상세 조회 시도: postId=${postId}`);
    try {
      const response = await apiClient.get(`/api/posts/${postId}`);
      console.log("게시글 상세 조회 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("게시글 상세 조회 실패:", err);
      throw err;
    }
  };
  
// 게시글 수정
export const updatePost = async (postId, postData) => {
    console.log(`게시글 수정 시도: postId=${postId}`, postData);
    try {
      const response = await apiClient.patch(`/api/posts/${postId}`, postData);
      console.log("게시글 수정 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("게시글 수정 실패:", err);
      throw err;
    }
  };
  
// 게시글 삭제
export const deletePost = async (postId) => {
    console.log(`게시글 삭제 시도: postId=${postId}`);
    try {
      const response = await apiClient.delete(`/api/posts/${postId}`);
      console.log("게시글 삭제 성공:", response.data);
      return response.data;
    } catch (err) {
      console.error("게시글 삭제 실패:", err);
      throw err;
    }
  };
  