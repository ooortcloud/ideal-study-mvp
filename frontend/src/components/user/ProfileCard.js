// 회원 목록에서 보이는 회원프로필카드
import React from "react";
import { useNavigate } from "react-router-dom";
import styles from "./ProfileCard.module.css";

const ProfileCard = ({ user }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    user.role === "teacher"
      ? navigate(`/officialPage/:id`)
      : navigate(`/myPage/${user.id}`); // 상세 조회 페이지로 이동
  };

  // 디버깅
  console.log(user);

  return (
    <div className={styles["profile-card"]} onClick={handleClick}>
      <img src={user.avatar} alt={`${user.name} 프로필`} />
      <h3>{user.name}</h3>
      <p>Role: {user.role}</p>
    </div>
  );
};

export default ProfileCard;
