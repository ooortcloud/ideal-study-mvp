import React from "react";
import EnrollmentList from "../../../../components/classroom/preClass/enrollment/EnrollmentList";

const EnrollmentBoardPage = ({ userInfo }) => {
  return (
    <div>
      <h2>수업신청 목록조회 페이지를 만듦</h2>
      <EnrollmentList />
    </div>
  );
};

export default EnrollmentBoardPage;
