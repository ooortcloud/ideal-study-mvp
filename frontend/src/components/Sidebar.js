import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <h3>마이 페이지</h3>
      <nav>
        <Link to="/profile">개인정보 조회/수정</Link>
        <Link to="/classes">내 클래스 목록</Link>
        <Link to="/settings">설정</Link>
      </nav>
    </aside>
  );
};

export default Sidebar;
