import React, { useContext } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  useLocation,
} from "react-router-dom";
import { AuthProvider, AuthContext } from "./context/AuthContext";
import HomePage from "./pages/home/HomePage";
import SignUpPage from "./pages/auth/SignUpPage";
import SignUpCompletePage from "./pages/auth/SignUpCompletePage";
import LoginPage from "./pages/auth/LoginPage";

import Header from "./components/Header";
import Footer from "./components/Footer";
import ProfileListPage from "./pages/user/ProfileListPage";
import ProfilePage from "./pages/user/ProfilePage";
import NotFound from "./pages/NotFound";
import OfficialProfilePage from "./pages/teacher/OfficialProfilePage";
import OfficialProfilePageUpdate from "./pages/teacher/OfficialProfilePageUpdate";

import "./App.css"; // 스타일 파일 import
import Sidebar from "./components/Sidebar";
import ClassroomListPage from "./pages/classroom/ClassroomListPage";
import ClassroomDetailPage from "./pages/classroom/ClassroomDetailPage";
import InquiyBoard from "./pages/classroom/preClass/inquiry/InquiryBoardPage";
import InquiryForm from "./components/classroom/preClass/inquiry/InquiryForm";
import InquiryDetailPage from "./pages/classroom/preClass/inquiry/InquiryDetailPage";
import ClassroomForm from "./components/classroom/ClassroomForm";
import RecordedLectureForm from "./components/classroom/inClass/RecordedLecture/RecordedLectureForm";
import RecordedLectureListPage from "./pages/classroom/inClass/RecordedLecture/RecordedLectureListPage";
import RecordedLectureDetailPage from "./pages/classroom/inClass/RecordedLecture/RecordedLectureDetailPage";
import ClassroomCreatePage from "./pages/classroom/ClassroomCreatePage";
import StudentListPage from "./pages/teacher/teacherRoom/StudentListPage";
import AssignmentListPage from "./pages/teacher/teacherRoom/AssignmentListPage";
import AssignmentSubmissionsPage from "./pages/teacher/teacherRoom/AssignmentSubmissionsPage";
import ExamSubmissionsPage from "./pages/teacher/teacherRoom/ExamSubmissionsPage";
import ExamListPage from "./pages/teacher/teacherRoom/ExamListPage";
import StudyRoomPage from "./pages/student/StudyRoomPage";
import AssignmentManagementPage from "./pages/student/studentRoom/AssignmentManagementPage";
import ExamManagementPage from "./pages/student/studentRoom/ExamManagementPage";
import TeacherRoomPage from "./pages/teacher/TeacherRoomPage";
import StudentClassroomListPage from "./pages/student/studentRoom/StudentClassroomListPage";
import TeacherClassroomListPage from "./pages/teacher/teacherRoom/TeacherClassroomListPage";

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <AppContent />
      </Router>
    </AuthProvider>
  );
};

const AppContent = () => {
  const { logout, isAuthenticated, userInfo } = useContext(AuthContext);
  const location = useLocation();
  // 현재 경로 prefix
  const here = location.pathname.split("/")[1];

  return (
    <>
      <Header
        logout={logout}
        isAuthenticated={isAuthenticated}
        userInfo={userInfo}
      />
      <div className="page-common">
        <Sidebar location={here} userRole={userInfo.role} />
        <div className="page-content">
          <Routes>
            {/*TeacherRoom Routes */}
            <Route path="/teacherRoom">
              <Route path="classes" element={<TeacherClassroomListPage />} />
              <Route path="students" element={<StudentListPage />} />
              <Route path="assignments" element={<AssignmentListPage />} />
              <Route
                path="assignment-submissions"
                element={<AssignmentSubmissionsPage />}
              />
              <Route path="exams" element={<ExamListPage />} />
              <Route
                path="exam-submissions"
                element={<ExamSubmissionsPage />}
              />
              <Route path="" element={<TeacherRoomPage />} />
            </Route>

            {/*Student Routes */}
            <Route path="/studentRoom">
              <Route path="classes" element={<StudentClassroomListPage />} />
              <Route
                path="assignments"
                element={<AssignmentManagementPage />}
              />
              <Route path="exams" element={<ExamManagementPage />} />
              <Route path="" element={<StudyRoomPage />} />
            </Route>

            {/* 인강 업로드 */}
            <Route
              path="/recordedLecture/new/:classId"
              element={<RecordedLectureForm />}
            />
            {/* 인강 목록 */}
            <Route
              path="/recordedLecture/list/:classId"
              element={<RecordedLectureListPage />}
            />
            {/* 인강 상세 */}
            <Route
              path="/recordedLecture/:id"
              element={<RecordedLectureDetailPage />}
            />
            {/* main */}
            <Route path="/" element={<HomePage />} />
            {/* auth */}
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/signup-complete" element={<SignUpCompletePage />} />
            <Route path="/login" element={<LoginPage />} />
            {/* user */}
            <Route path="/teachers" element={<ProfileListPage />} />
            <Route path="/students" element={<ProfileListPage />} />
            <Route
              path="/myPage/:id"
              element={<ProfilePage userInfo={userInfo} />}
            />
            {/* teachers only */}
            <Route path="/officialPage/:id" element={<OfficialProfilePage />} />
            <Route
              path="/officialPageUpdate"
              element={<OfficialProfilePageUpdate />}
            />

            {/* 클래스 생성 */}
            <Route path="/classes/new" element={<ClassroomCreatePage />} />
            {/* 클래스 목록 */}
            <Route path="/classes" element={<ClassroomListPage />} />
            {/* 클래스 목록 (유저별) */}
            <Route
              path="/classes/user/:userId"
              element={<ClassroomListPage />}
            />
            {/* 클래스 수정 */}
            <Route path="/classes/:classId/edit" element={<ClassroomForm />} />
            {/* 클래스 상세 */}
            <Route
              path="/classes/:classId"
              element={<ClassroomDetailPage userInfo={userInfo} />}
            />

            {/* classroom - inquiry */}
            {/* 문의 목록 */}
            <Route
              path="/classes/:classId/inquiries"
              element={<InquiyBoard />}
            />
            {/* 문의 작성 */}
            <Route
              path="/classes/:classId/inquiries/new"
              element={<InquiryForm />}
            />
            {/* 문의 상세 */}
            <Route
              path="/classes/:classId/inquiries/:inquiryId"
              element={<InquiryDetailPage />}
            />
            {/* 문의 수정 */}
            <Route
              path="/classes/:classId/inquiries/:inquiryId/edit"
              element={<InquiryForm />}
            />
            {/* 일치하는 라우트가 없을 때 NotFound 컴포넌트 렌더링 */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default App;
