import apiClient from "./apiClient.mjs";
/**
 * 좋아요 추가/취소 API (댓글)
 */
export const toggleCommentLike = async (commentId) => {
  console.log("댓글 좋아요 추가/취소 API 시도:", { commentId });
  try {
    const response = await apiClient.post(`/api/likes/comments/${commentId}`);
    console.log("댓글 좋아요 추가/취소 API 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("댓글 좋아요 추가/취소 API 실패", err);
  }
};

/**
 * 좋아요 추가/취소 API (게시글)
 */
export const togglePostLike = async (postId) => {
  console.log("게시글 좋아요 추가/취소 API 시도:", { postId });
  try {
    const response = await apiClient.post(`/api/likes/posts/${postId}`);
    console.log("게시글 좋아요 추가/취소 API 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("게시글 좋아요 추가/취소 API 실패", err);
    throw err;
  }
};

/**
 * 좋아요 목록 조회 (스케줄러)
 */
export const readScheduleLikes = async (scheduleId) => {
  console.log("스케줄러 좋아요 목록 조회 API 시도:", { scheduleId });
  try {
    const response = await apiClient.get(`/api/likes/schedules/${scheduleId}`);
    console.log("스케줄러 좋아요 목록 조회 API 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("스케줄러 좋아요 목록 조회 API 실패", err);
    throw err;
  }
};
