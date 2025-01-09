import React from "react";

// 문의 목록을 표시하는 컴포넌트
const InquiryList = ({ inquiries, classId, onInquiryClick }) => {
  return (
    <table style={{ width: "100%", borderCollapse: "collapse" }}>
      <thead>
        <tr>
          <th style={{ width: "10%" }}>번호</th>
          <th style={{ width: "50%" }}>글 제목</th>
          <th style={{ width: "15%" }}>작성자</th>
          <th style={{ width: "15%" }}>공개 여부</th>
          <th style={{ width: "10%" }}>댓글 개수</th>
        </tr>
      </thead>
      <tbody>
        {inquiries.map((inquiry) => (
          <tr key={inquiry.id} onClick={() => onInquiryClick(inquiry.id)}>
            <td>{inquiry.id}</td>
            <td>{inquiry.title}</td>
            <td>{inquiry.author}</td>
            <td>{inquiry.isPublic ? "공개" : "비공개"}</td>
            <td>{inquiry.comments}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default InquiryList;
