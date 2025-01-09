// 방명록
import React, { useState } from "react";
import Button from "../Button";

const Bio = ({
  user,
  fetchedBio,
  isAuthenticated,
  isEditing,
  setIsEditing,
  onSave,
}) => {
  const [bio, setBio] = useState(fetchedBio?.bio || "");

  const handleChange = (e) => {
    setBio(e.target.value); // 입력값 상태 업데이트
  };

  return (
    <div>
      <h3>자기소개</h3>
      <div>
        {isEditing ? (
          <textarea
            value={bio}
            onChange={handleChange}
            style={{
              width: "100%",
              height: "100px",
              resize: "vertical",
            }}
          />
        ) : (
          <div>{bio || "자기소개가 없습니다."}</div>
        )}
      </div>
      <br />
      {isAuthenticated && (
        <>
          {isEditing ? (
            <Button onClick={() => onSave(bio)}>저장</Button> // 저장 버튼
          ) : (
            <Button onClick={() => setIsEditing(true)}>수정</Button> // 수정 버튼
          )}
        </>
      )}
    </div>
  );
};

export default Bio;
