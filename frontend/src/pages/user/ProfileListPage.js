import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ProfileCard from '../../components/user/ProfileCard';
import { readUsers } from '../../services/user/UserService.mjs';
import styles from './ProfileListPage.module.css';

const ProfileListPage = () => {
  const [profiles, setProfiles] = useState([]);
  const location = useLocation();

  useEffect(() => {
    const fetchProfiles = async () => {
      const data = await readUsers(location.pathname); // 데이터 자체를 반환받음
      setProfiles(data.dtoList); // 그대로 profiles에 설정

    };
    fetchProfiles();
  }, [location.pathname]);

  return (
    <div>
      <h2>{location.pathname === '/teachers' ? '선생님 목록입니다' : '학생 목록입니다'}</h2>
      <div className={styles['profile-grid']}>
        {profiles.map((user) => (
          <ProfileCard key={user.userId} user={user} />
        ))}
      </div>
    </div>
  );
};

export default ProfileListPage;