// 생성, 수정시 동시사용

import React, { useState } from "react";
import {
  createInquiry,
  updateInquiry,
} from "../../../../services/classroom/InquiryService.mjs";
import { useNavigate, useParams } from "react-router-dom";

const InquiryForm = ({ initialData = null }) => {
  const { inquiryId } = useParams(); // 수정시에만 존재
  const navigate = useNavigate();
  const [formData, setFormData] = useState(
    initialData || { title: "", content: "", isPublic: true }
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (inquiryId) {
      await updateInquiry(inquiryId, formData);
    } else {
      await createInquiry(formData);
    }
    navigate(`/inquiries`);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        제목:
        <input
          type="text"
          value={formData.title}
          onChange={(e) => setFormData({ ...formData, title: e.target.value })}
        />
      </label>
      <label>
        내용:
        <textarea
          value={formData.content}
          onChange={(e) =>
            setFormData({ ...formData, content: e.target.value })
          }
        />
      </label>
      <label>
        공개 여부:
        <select
          value={formData.isPublic}
          onChange={(e) =>
            setFormData({ ...formData, isPublic: e.target.value === "true" })
          }
        >
          <option value="true">공개</option>
          <option value="false">비공개</option>
        </select>
      </label>
      <button type="submit">{inquiryId ? "수정" : "생성"}</button>
    </form>
  );
};

export default InquiryForm;
