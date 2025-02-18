import React from "react";
import { useNavigate } from "react-router-dom";
import ClassroomForm from "../../components/classroom/ClassroomForm";
import { createClass } from "../../services/classroom/ClassroomService.mjs";
import "./ClassroomCreatePage.css";

const ClassroomCreatePage = () => {
  const navigate = useNavigate();

  const handleSubmit = async (formData) => {
    try {
      const response = await createClass(formData);
      console.log("클래스 생성 response", response);
      if (response.status === "SETUP") {
        alert("클래스 생성 완료");
        navigate(`/classes/${response.id}`); // 클래스 목록 페이지로 이동
      }
    } catch (error) {
      alert("클래스 생성 실패");
    }
  };

  return (
    <div className="page-container">
      <h1 className="page-title">클래스 생성</h1>
      <div className="form-wrapper">
        <ClassroomForm onSubmit={handleSubmit} />
      </div>
    </div>
  );
};

export default ClassroomCreatePage;
