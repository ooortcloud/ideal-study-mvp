import React, { useState, useEffect } from "react";
import {
  readEnrollmentsByStudentId,
  readEnrollmentsByStudentJWT,
} from "../../../../services/classroom/EnrollmentService.mjs";
import useAuthStore from "../../../../stores/authStore";

const EnrollmentList = () => {
  const [enrollments, setEnrollments] = useState([]);
  const [enrollmentsParent, setEnrollmentsParent] = useState([]);
  const userInfo = useAuthStore((state) => state.userInfo);

  useEffect(() => {
    const fetchData = async () => {
      if (userInfo.role === "student") {
        const data = await readEnrollmentsByStudentJWT();
        setEnrollments(data);
      } else if (userInfo.role === "parent") {
        const dataParent = await readEnrollmentsByStudentId(userInfo.id);
        setEnrollmentsParent(dataParent);
      }
    };
    fetchData();
  }, [userInfo.role]);

  return (
    <div>
      <p>
        {userInfo.role === "student"
          ? "학생 셀프로 조회한 수업신청 목록"
          : "학부모가 조회한 수업신청 목록"}
      </p>
      {enrollments.map((enrollment) => (
        <div key={enrollment.id}>
          {enrollment.name}
          <button>수업신청 수정</button>
          <button>클래스 상세조회</button>
        </div>
      ))}
      <p>학부모가 조회한 수업신청목록</p>
      {enrollmentsParent.map((parentEnrollment) => (
        <div key={parentEnrollment.id}>
          {parentEnrollment.name}
          <button>수업신청 수정</button>
          <button>클래스 상세조회</button>
        </div>
      ))}
    </div>
  );
};

export default EnrollmentList;
