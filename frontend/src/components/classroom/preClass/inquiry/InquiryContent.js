import React, { useContext, useEffect, useState } from "react";
import {
  deleteInquiry,
  readInquiry,
} from "../../../../services/classroom/InquiryService.mjs";
import { useNavigate, useParams } from "react-router-dom";
import { AuthContext } from "../../../../context/AuthContext";
import Button from "../../../../components/Button.js";

// 문의 상세정보 및 수정삭제
const InquiryContent = () => {
  const { inquiryId, classId } = useParams();
  const [inquiries, setInquiries] = useState([]);
  const navigate = useNavigate();
  const { userInfo } = useContext(AuthContext);

  useEffect(() => {
    const fetchInquiry = async () => {
      const data = await readInquiry(inquiryId);
      setInquiries(data);
    };
    fetchInquiry();
  }, [inquiryId]);

  const handleDelete = async () => {
    await deleteInquiry(inquiryId);
    navigate(`/classes/${classId}/inquiries`);
  };

  return inquiries ? (
    <div className="inquiries-detail">
      <Button onClick={() => navigate(`/classes/${classId}/inquiries`)}>
        문의 목록으로
      </Button>
      <h2>{inquiries.title}</h2>
      <p>{inquiries.content}</p>
      {userInfo.id === inquiries.authorId && (
        <>
          <button
            onClick={() =>
              navigate(`/classes/${classId}/inquiries/${inquiries.id}/edit`)
            }
          >
            수정
          </button>
          <button onClick={handleDelete}>삭제</button>
        </>
      )}
    </div>
  ) : (
    <p>로딩 중...</p>
  );
};

export default InquiryContent;
