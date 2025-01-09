import React, { useState } from 'react';
import EnrollmentForm from './EnrollmentForm';
import enrollmentFormModalcss from './EnrollmentFormModalcss';
import Modal from 'react-modal';

const Enrollment = ({classId}) => {

    const [isOpen, setIsOpen] = useState(false);

    // 모달 열기
    const openModal = () => {
        setIsOpen(true);
        console.log("모달 open");
    }

    // 모달 닫기
    const closeModal = () => {
        setIsOpen(false);
        console.log("모달 close");
    }

    return (
        <div style={{ display:"flex" }}>
            <h2>수업 신청</h2> &nbsp;
            
            <button onClick ={openModal}> 신청하기 </button>
            <Modal
                isOpen = {isOpen}
                // onRequestClose={closeModal} 모달을 닫으려고 할 때 실행되는 함수
                style={enrollmentFormModalcss} 
            >
                <EnrollmentForm classId={classId} isClose={closeModal} />
            </Modal>
        </div>
    );
};

export default Enrollment;