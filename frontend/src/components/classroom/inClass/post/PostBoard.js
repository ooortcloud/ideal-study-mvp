import React from "react";

const PostBoard = ({ classId }) => {
  return (
    <div className="class-posts">
      <h2>수업글</h2>
      {/* 게시글 리스트 추가 */}
      <p>수업과 관련된 글(공지,과제,자유)들이 여기에 표시됩니다.</p>
    </div>
  );
};

export default PostBoard;
