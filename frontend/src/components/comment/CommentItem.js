import React, { useState } from "react";
import CommentForm from "./CommentForm";
import styles from "./CommentItem.module.css";

const CommentItem = ({
  comment,
  onReply,
  onLike,
  onDelete,
  onEdit,
  currentUserId,
}) => {
  const [isEditing, setIsEditing] = useState(false);
  const [isReplying, setIsReplying] = useState(false);

  return (
    <div className={styles.item}>
      <div className={styles.header}>
        <span className={styles.author}>{comment.author}</span>
        <span className={styles.date}>{comment.createdAt}</span>
        <span className={styles.likeCount}>❤{comment.likes}</span>
      </div>

      {isEditing ? (
        <CommentForm
          initialValue={comment.content}
          onSubmit={(content) => {
            onEdit(comment.id, content);
            setIsEditing(false);
          }}
          onCancel={() => setIsEditing(false)}
        />
      ) : (
        <div className={styles.content}>{comment.content}</div>
      )}

      <div className={styles.actions}>
        <button className={styles.button} onClick={() => onLike(comment.id)}>
          좋아요 {comment.likeCount}
        </button>
        {comment.parentId === null && (
          <button className={styles.button} onClick={() => setIsReplying(true)}>
            답글
          </button>
        )}
        {currentUserId === comment.authorId && (
          <>
            <button
              className={styles.button}
              onClick={() => setIsEditing(true)}
            >
              수정
            </button>
            <button
              className={styles.button}
              onClick={() => onDelete(comment.id)}
            >
              삭제
            </button>
          </>
        )}
      </div>

      {isReplying && (
        <CommentForm
          onSubmit={(content) => {
            onReply(comment.id, content);
            setIsReplying(false);
          }}
          onCancel={() => setIsReplying(false)}
          placeholder="대댓글을 입력하세요..."
        />
      )}

      {comment.replies && comment.replies.length > 0 && (
        <div className={styles.replies}>
          {comment.replies.map((reply) => (
            <CommentItem
              key={reply.id}
              comment={reply}
              onReply={onReply}
              onLike={onLike}
              onDelete={onDelete}
              onEdit={onEdit}
              currentUserId={currentUserId}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default CommentItem;
