import React, { useState, useEffect } from "react";
import {
  createSchedule,
  readSchedulesByClass,
} from "../../../../services/classroom/ScheduleService.mjs";
import ScheduleCalendar from "./ScheduleCalendar";
import ScheduleDetails from "./ScheduleDetails";
import styles from "./ScheduleBoard.css";

const ScheduleBoard = ({ classId }) => {
  const [date, setDate] = useState(new Date()); // 현재 시간
  const [schedules, setSchedules] = useState([]); // 저장된 스케쥴
  const [selectedSchedule, setSelectedSchedule] = useState(null); // 클릭한 일정

  // 클래스별 스케쥴 목록 조회
  useEffect(() => {
    const fetchSchedule = async () => {
      const data = await readSchedulesByClass(classId);
      setSchedules(data);
    };
    fetchSchedule();
  }, [classId]);

  // 날짜 이동 이벤트
  const handleDateChange = (newDate) => {
    setDate(newDate);
  };

  // 날짜 선택 이벤트
  const handleDateClick = (date) => {
    const schedule = schedules.find(
      (schedule) => schedule.date.toDateString() === date.toDateString()
    );
    setSelectedSchedule(schedule);
  };

  // 스케쥴 등록
  const handleAddSchedule = async () => {
    const newSchedule = {
      date: date,
      title: prompt("일정 제목을 입력하세요."),
      content: prompt("일정 세부 내용을 입력하세요."),
    };
    if (newSchedule.title && newSchedule.content) {
      setSchedules((prevSchedules) => [...prevSchedules, newSchedule]);
      await createSchedule(classId, newSchedule);
    }
  };

  return (
    <div>
      <h3>수업 스케줄</h3>
      <div className="schedules-board">
        <div className="calendar-container">
          <ScheduleCalendar
            schedules={schedules} // 저장된 일정목록
            currentDate={date} // 현재 날짜
            dateChange={handleDateChange} // 날짜 이동
            clickDay={handleDateClick} // 날짜 선택
          />
        </div>
        <div className="details-container">
          {selectedSchedule && (
            <ScheduleDetails // 상세 일정조회
              schedule={selectedSchedule}
            />
          )}
        </div>
      </div>
      <br />
      <button onClick={handleAddSchedule}>일정 등록</button>
    </div>
  );
};

export default ScheduleBoard;
