import apiClient from "./apiClient.mjs";

export const createComment = async (inquiryId, parentId, commentData) => {
  const requestData = {
    inquiryId,
    parentId,
    ...commentData,
  };

  console.log("댓글 등록 시도:", requestData);
  try {
    const response = await apiClient.post("/api/comments", requestData);
    console.log("댓글 등록 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("댓글 등록 실패:", err);

    // API 호출 실패시 임시 데이터 반환
    const mockComment = {
      id: Math.floor(Math.random() * 1000),
      inquiryId,
      parentId,
      content: `${commentData.content} (수정 API 전달안됨)`,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      likes: 0,
      replies: [],
    };
    return mockComment;
  }
};

// 댓글 목록 조회 (게시글별)
export const readCommentsByPostId = async (postId) => {
  console.log(`댓글 목록 조회 (게시글별) 시도: postId=${postId}`);
  try {
    const response = await apiClient.get(`/api/comments/posts/${postId}`);
    console.log("댓글 목록 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("댓글 목록 조회 실패:", err);

    // Mock 데이터
    const mockComments = [
      {
        id: 1,
        postId: 1,
        parentId: null,
        content: "정말 유익한 게시글이네요!",
        author: "김철수",
        createdAt: "2024-03-20T10:00:00Z",
        updatedAt: "2024-03-20T10:00:00Z",
        likes: 10,
        replies: [
          {
            id: 4,
            postId: 1,
            parentId: 1, // 부모 댓글의 ID
            content: "동의합니다!",
            author: "홍길동",
            createdAt: "2024-03-20T10:30:00Z",
            updatedAt: "2024-03-20T10:30:00Z",
            likes: 2,
            replies: [],
          },
          {
            id: 5,
            postId: 1,
            parentId: 1,
            content: "저도 같은 생각입니다.",
            author: "이영희",
            createdAt: "2024-03-20T11:30:00Z",
            updatedAt: "2024-03-20T11:30:00Z",
            likes: 5,
            replies: [],
          },
        ],
      },
      {
        id: 2,
        postId: 1,
        parentId: null,
        content: "저도 같은 생각입니다.",
        author: "이영희",
        createdAt: "2024-03-20T11:30:00Z",
        updatedAt: "2024-03-20T11:30:00Z",
        likes: 5,
        replies: [
          {
            id: 6,
            postId: 1,
            parentId: 2,
            content: "취소합니다.",
            author: "이영희",
            createdAt: "2024-03-20T11:30:00Z",
            updatedAt: "2024-03-20T11:30:00Z",
            likes: 5,
            replies: [],
          },
        ],
      },
      {
        id: 3,
        postId: 1,
        parentId: null,
        content: "다음 게시글도 기대됩니다!",
        author: "박지민",
        createdAt: "2024-03-20T12:15:00Z",
        updatedAt: "2024-03-20T12:15:00Z",
        likes: 3,
      },
    ];
    return mockComments;
  }
};

// 댓글 상세 조회
export const readComment = async (commentId) => {
  console.log(`댓글 상세 조회 시도: commentId=${commentId}`);
  try {
    const response = await apiClient.get(`/api/comments/${commentId}`);
    console.log("댓글 상세 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("댓글 상세 조회 실패:", err);
  }
};

// 댓글 수정
export const updateComment = async (commentId, commentData) => {
  console.log(`댓글 수정 시도: commentId=${commentId}`, commentData);
  try {
    const response = await apiClient.patch(
      `/api/comments/${commentId}`,
      commentData
    );
    console.log("댓글 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("댓글 수정 실패:", err);
  }
};

// 댓글 삭제
export const deleteComment = async (commentId) => {
  console.log(`댓글 삭제 시도: commentId=${commentId}`);
  try {
    const response = await apiClient.delete(`/api/comments/${commentId}`);
    console.log("댓글 삭제 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("댓글 삭제 실패:", err);
  }
};
