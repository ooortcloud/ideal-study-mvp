import React from 'react';
import Calendar from 'react-calendar'; // 리액트 캘린더
import 'react-calendar/dist/Calendar.css'; // 리액트 캘린더 css
import './reactCalendar.css';

// 전달받은 props에 따라 화면을 그리는 역할에 집중

const ScheduleCalendar = ({ schedules, currentDate, dateChange, clickDay }) => {
    
    return (
        <Calendar 
          onChange={dateChange} 
          value={currentDate} 
          onClickDay={clickDay}
          // 날짜 안에 보이는 컨텐츠
          tileContent={({date}) => {
            const schedule = schedules.find(
              (e) => e.date.toDateString() === date.toDateString()
            )
            return schedule? <p>🏊‍♂️{schedule.title}</p> : null
          }}
          // 스케쥴러 css
          tileClassName 
        />
    );
};

export default ScheduleCalendar;