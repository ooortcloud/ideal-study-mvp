import React, { useState } from "react";
import styles from "./CommentForm.module.css";

// 댓글 입력 폼 컴포넌트
const CommentForm = ({
  initialValue = "",
  onSubmit,
  onCancel,
  placeholder = "댓글을 입력하세요...",
}) => {
  const [content, setContent] = useState(initialValue);

  return (
    <div className={styles.container}>
      <form
        className={styles.form}
        onSubmit={(e) => {
          e.preventDefault();
          onSubmit(content);
          setContent("");
        }}
      >
        <textarea
          className={styles.textarea}
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder={placeholder}
        />
        <div className={styles.actions}>
          <button className={styles.submitButton} type="submit">
            등록
          </button>
          {onCancel && (
            <button className={styles.cancelButton} onClick={onCancel}>
              취소
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default CommentForm;
