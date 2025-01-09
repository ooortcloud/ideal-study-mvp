import { useParams } from "react-router-dom";
import React, { useState, useEffect, useContext } from "react";
import {
  createComment,
  deleteComment,
  readCommentsByPostId,
  updateComment,
} from "../../../../services/CommentService.mjs";
import { toggleCommentLike } from "../../../../services/LikeService.mjs";
import { AuthContext } from "../../../../context/AuthContext";
import { readInquiry } from "../../../../services/classroom/InquiryService.mjs";
import "./InquiryDetailPage.css";
import InquiryContent from "../../../../components/classroom/preClass/inquiry/InquiryContent";
import CommentList from "../../../../components/comment/CommentList";

const InquiryDetailPage = () => {
  const { inquiryId } = useParams();
  const [inquiry, setInquiry] = useState(null);
  const [comments, setComments] = useState([]);
  const { userInfo } = useContext(AuthContext);

  useEffect(() => {
    const fetchData = async () => {
      const inquiryData = await readInquiry(inquiryId);
      const commentsData = await readCommentsByPostId(inquiryId);
      setInquiry(inquiryData);
      setComments(commentsData);
    };
    fetchData();
  }, [inquiryId]);

  const handleAddComment = async (content) => {
    const newComment = await createComment(inquiryId, null, { content });
    setComments((prev) => [...prev, newComment]);
  };

  const handleReply = async (parentId, content) => {
    const newReply = await createComment(inquiryId, parentId, { content });
    // 대댓글이 등록되면 해당 부모댓글(parentId) 하위에 대댓글 추가
    setComments((prev) => {
      return prev.map((comment) => {
        if (comment.id === parentId) {
          return {
            ...comment,
            replies: [...comment.replies, newReply],
          };
        }
        return comment;
      });
    });
  };

  const handleLike = async (commentId) => {
    // 좋아요 상태 업데이트 로직
    setComments((prev) =>
      prev.map((comment) => {
        // 좋아요를 누른 댓글이 부모일 경우
        if (comment.id === commentId) {
          return {
            ...comment,
            likes: comment.likes + 1,
            isLiked: !comment.isLiked,
          };
        }
        // 좋아요를 누른 댓글이 대댓글일 경우
        if (comment.replies) {
          return {
            ...comment,
            replies: comment.replies.map((reply) =>
              reply.id === commentId
                ? {
                    ...reply,
                    likes: reply.likes + 1,
                    isLiked: !reply.isLiked,
                  }
                : reply
            ),
          };
        }
        return comment;
      })
    );
    // 좋아요 상태 업데이트 API 호출
    await toggleCommentLike(commentId);
  };

  const handleDeleteComment = async (commentId) => {
    await deleteComment(commentId);
    // TODO : 댓글 삭제 로직
  };

  const handleEditComment = async (commentId, content) => {
    await updateComment(commentId, { content });
    // TODO : 댓글 수정 로직
  };

  return (
    <div className="inquiry-detail-page">
      <InquiryContent inquiry={inquiry} />

      <div className="comments-header">
        <h3 className="comments-count">
          <span className="count">{comments.length}</span>
          <span className="text">개의 댓글</span>
        </h3>
      </div>
      {console.log(
        "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"
      )}
      <CommentList
        comments={comments}
        onAddComment={handleAddComment}
        onReply={handleReply}
        onLike={handleLike}
        onDelete={handleDeleteComment}
        onEdit={handleEditComment}
        currentUserId={userInfo.id}
      />
    </div>
  );
};

export default InquiryDetailPage;
