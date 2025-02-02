import React, { useState, useContext } from "react";
import EnrollmentForm from "./EnrollmentForm";
import enrollmentFormModalcss from "./EnrollmentFormModalcss";
import { AuthContext } from "../../../../context/AuthContext";
import Modal from "react-modal";

const Enrollment = ({ classId }) => {
  const [isOpen, setIsOpen] = useState(false);
  const { userInfo } = useContext(AuthContext);

  // 모달 열기
  const openModal = () => {
    console.log(userInfo);
    if (userInfo.role !== "student" && userInfo.role !== "parent") {
      alert("학생만 신청할 수 있어요");
      return; // 모달을 열지 않도록 종료
    }
    setIsOpen(true);
    console.log("모달 open");
  };

  // 모달 닫기
  const closeModal = () => {
    setIsOpen(false);
    console.log("모달 close");
  };

  return (
    <div style={{ display: "flex" }}>
      <h2>수업 신청</h2> &nbsp;
      <button onClick={openModal}> 신청하기 </button>
      <Modal
        isOpen={isOpen}
        // onRequestClose={closeModal} 모달을 닫으려고 할 때 실행되는 함수
        style={enrollmentFormModalcss}
      >
        <EnrollmentForm
          classId={classId}
          isClose={closeModal}
          userInfo={userInfo}
        />
      </Modal>
    </div>
  );
};

export default Enrollment;
