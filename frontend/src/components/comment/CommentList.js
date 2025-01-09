import React from "react";
import CommentForm from "./CommentForm";
import CommentItem from "./CommentItem";
import styles from "./CommentList.module.css";

// 댓글 목록 컴포넌트
const CommentList = ({
  comments,
  onAddComment,
  onReply,
  onLike,
  onDelete,
  onEdit,
  currentUserId,
}) => {
  return (
    <div className={styles.commentList}>
      <div className={styles.formContainer}>
        <CommentForm onSubmit={onAddComment} />
      </div>

      <div className={styles.commentsContainer}>
        {comments.map((comment) => (
          <CommentItem
            key={comment.id}
            comment={comment}
            onReply={onReply}
            onLike={onLike}
            onDelete={onDelete}
            onEdit={onEdit}
            currentUserId={currentUserId}
          />
        ))}
      </div>
    </div>
  );
};

export default CommentList;
