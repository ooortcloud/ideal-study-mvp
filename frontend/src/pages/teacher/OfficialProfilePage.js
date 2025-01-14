import React, { useEffect, useState, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import { ReadOfficialProfile } from "../../services/OfficialProfileService.mjs";
import Button from "../../components/Button";

const OfficialProfilePage = () => {
  const { userInfo } = useContext(AuthContext);
  const { id } = useParams();
  const navigate = useNavigate();

  const [profileContent, setProfileContent] = useState(""); // 프로필 내용 상태

  // 컴포넌트 마운트 시 API 호출
  useEffect(() => {
    const fetchProfile = async () => {
      const data = await ReadOfficialProfile(1); // 예시로 userId 1 전달
      setProfileContent(data.content); // API 응답에서 content만 상태에 저장
    };

    fetchProfile();
  }, []);

  const handleUpdateOfficialProfilePage = () => {
    navigate("/myPage/officialPageUpdate", {
      state: { id }, // userInfo와 id를 state로 넘김
    });
  };

  return (
    <div>
      {/* HTML 내용 렌더링 */}
      <div dangerouslySetInnerHTML={{ __html: profileContent }} />
      {userInfo.id === id && (
        <Button onClick={handleUpdateOfficialProfilePage}>수정</Button>
      )}
    </div>
  );
};

export default OfficialProfilePage;
