import React from "react";

// 문의 게시판 헤더 컴포넌트
const InquiryHeader = ({ classId, onNewInquiry }) => {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
      }}
    >
      <h2>문의 게시판</h2>
      <button onClick={onNewInquiry}>문의 하기</button>
    </div>
  );
};

export default InquiryHeader;
