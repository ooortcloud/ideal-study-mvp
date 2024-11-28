import React from 'react';

const ScheduleBoard = ({ classId }) => {
  return (
    <div className="class-schedule">
      <h2>클래스 스케줄러</h2>
      {/* 일정 정보 추가 */}
      <p>클래스 일정이 이 달력에 표시됩니다.</p>
    </div>
  );
};

export default ScheduleBoard;
