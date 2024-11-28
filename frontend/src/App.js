import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import HomePage from './pages/home/HomePage'
import SignUpPage from './pages/auth/SignUpPage'
import SignUpCompletePage from './pages/auth/SignUpCompletePage'
import LoginPage from './pages/auth/LoginPage'

import Header from './components/Header';
import Footer from './components/Footer';
import ProfileListPage from './pages/user/ProfileListPage';
import ProfilePage from './pages/user/ProfilePage';
import NotFound from './pages/NotFound';
import OfficialProfilePage from './pages/teacher/OfficialProfilePage';
import OfficialProfilePageUpdate from './pages/teacher/OfficialProfilePageUpdate';

import './App.css';  // 스타일 파일 import
import Sidebar from './components/Sidebar';
import ClassroomListPage from './pages/classroom/ClassroomListPage';
import ClassroomPage from './pages/classroom/ClassroomPage';

const App = () => {
  return (
    <AuthProvider>
      <Router>
      <Header />
      <div className="page-common">
        <Sidebar/> 
        <div className="page-content">
          <Routes>
            {/* main */}
            <Route path="/" element={<HomePage />} />
            {/* auth */}
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/signup-complete" element={<SignUpCompletePage />} />
            <Route path="/login" element={<LoginPage />} />
            {/* user */}
            <Route path="/teachers" element={<ProfileListPage />} />
            <Route path="/students" element={<ProfileListPage />} />
            <Route path="/myPage/:id" element={<ProfilePage/>} />
            {/* teachers only */}
            <Route path="/officialPage/:id" element={<OfficialProfilePage/>} />
            <Route path="/officialPageUpdate" element={<OfficialProfilePageUpdate/>} />
            {/* classroom */}
            <Route path="/classes" element={<ClassroomListPage/>} />
            <Route path="/classes/:id" element={<ClassroomPage/>} />
            {/* 일치하는 라우트가 없을 때 NotFound 컴포넌트 렌더링 */}
            <Route path="*" element={<NotFound/>} />
          </Routes>
        </div>
      </div>
      <Footer />
      </Router>
    </AuthProvider>
  );
};

export default App;
