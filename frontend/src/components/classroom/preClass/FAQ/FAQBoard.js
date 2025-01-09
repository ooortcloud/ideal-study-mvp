import React, { useState, useEffect } from "react";
import { readFAQsByClassId } from "../../../../services/classroom/FAQService.mjs";

const FAQBoard = ({ classId }) => {
  const [faqs, setFaqs] = useState([]);

  useEffect(() => {
    // 클래스별 문의 목록 가져오기
    const fetchClasses = async () => {
      const data = await readFAQsByClassId();
      setFaqs(data);
    };
    fetchClasses();
  }, []);

  return (
    <div className="faq-board">
      <h2>FAQ 게시판</h2>
      {/* FAQ 리스트 추가 */}
      <p>FAQ 글들이 여기에 표시됩니다.</p>
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>글 제목</th>
            <th>작성자</th>
            <th>공개 여부</th>
            <th>댓글 개수</th>
          </tr>
        </thead>
        <tbody>
          {faqs.map((faq) => (
            <tr key={faq.id}>
              <td>{faq.id}</td>
              <td>{faq.title}</td>
              <td>{faq.author}</td>
              <td>{faq.isPublic ? "공개" : "비공개"}</td>
              <td>{faq.comments}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default FAQBoard;
