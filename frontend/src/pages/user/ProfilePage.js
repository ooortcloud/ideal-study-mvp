import React, { useEffect, useState } from "react";

import { useParams } from "react-router-dom";
import { readUser, updateUser } from "../../services/user/UserService.mjs";
import { readBio, updateBio } from "../../services/MyPageService.mjs";

import UserInfoSpace from "../../components/user/UserInfoSpace";
import Photos from "../../components/user/Photos";
import Schedular from "../../components/user/Schedular";
import GuestBook from "../../components/user/GuestBook";

import "./ProfilePage.css";
import Bio from "../../components/user/Bio";
import Button from "../../components/Button";

const ProfilePage = ({ userInfo }) => {
  const { id } = useParams();
  const [user, setUser] = useState(null); // 유저 정보
  const [bio, setBio] = useState(null); // 유저 자기소개
  const [isEditingUser, setIsEditingUser] = useState(false); // 사용자 정보 수정 모드
  const [isEditingBio, setIsEditingBio] = useState(false); // 자기소개 수정 모드

  useEffect(() => {
    const fetchUserProfile = async () => {
      const data = await readUser(id);
      setUser(data);
    };

    const fetchReadBio = async () => {
      const data = await readBio(id);
      setBio(data);
    };

    fetchUserProfile();
    fetchReadBio();
  }, [id]);

  if (!user) return <p>Loading...</p>;

  // 디버깅
  console.log("userInfo.id", userInfo);
  console.log("id", id);
  console.log("본인여부", userInfo.id === id);
  console.log(typeof userInfo.id, typeof id);
  // 개인정보
  const handleUserUpdate = async (newUserInfo) => {
    await updateUser(userInfo.id, user);
    setIsEditingUser(false); // 수정 모드 종료
  };

  const handleBioUpdate = async (newBio) => {
    const updatedBio = await updateBio(userInfo.id, newBio);
    setBio(updatedBio); // 업데이트된 자기소개 내용 반영
    setIsEditingBio(false); // 수정 모드 종료
  };

  return (
    <div className="profile-container">
      <div className="section">
        <UserInfoSpace
          user={user}
          isAuthenticated={userInfo.id === id}
          isEditing={isEditingUser}
          setIsEditing={setIsEditingUser}
          onSave={handleUserUpdate} // 저장 함수 전달
        />
      </div>
      <div className="section">
        <Bio
          user={user}
          fetchedBio={bio}
          isAuthenticated={userInfo.id === id}
          isEditing={isEditingBio}
          setIsEditing={setIsEditingBio}
          onSave={handleBioUpdate} // 저장 함수 전달
        />
      </div>
      <div className="section unOpend">
        <Photos isAuthenticated={userInfo.id === id} />
      </div>
      <div className="section unOpend">
        <Schedular isAuthenticated={userInfo.id === id} />
      </div>
      <div className="section unOpend">
        <GuestBook isAuthenticated={userInfo.id === id} />
      </div>
    </div>
  );
};

export default ProfilePage;
