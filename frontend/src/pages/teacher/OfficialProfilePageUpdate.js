import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import TinyMCEeditor from "../../components/WYSIWYG/TinyMCEeditor";
import { UpdateOfficialProfile } from "../../services/OfficialProfileService.mjs";
import Button from "../../components/Button";

const OfficialProfilePageUpdate = () => {
  const location = useLocation(); // useLocation 훅을 사용하여 state에 접근
  const { id } = location.state || {}; // userInfo와 id를 state에서 받음
  const navigate = useNavigate();

  const [editorContent, setEditorContent] = useState("");

  // TinyMCE 초기화 및 내용 저장
  const handleEditorChange = (content) => {
    setEditorContent(content);
  };

  // 폼 제출
  const handleSubmit = (e) => {
    e.preventDefault();

    // 에디터 내용 확인
    console.log(editorContent);

    // 에디터 변경내용 서버로 제출
    UpdateOfficialProfile(editorContent);

    // 제출완료시 공식페이지로 이동
    navigate(`/myPage/officialPage/${id}`);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <TinyMCEeditor onChange={handleEditorChange} />
        <Button type="submit">제출</Button>
      </form>
    </div>
  );
};

export default OfficialProfilePageUpdate;
