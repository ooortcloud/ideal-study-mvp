import React from 'react';

const ScheduleDetails = ({ schedule }) => {
    return (
        <div className="schedule-details">
            <h1>🎉Event</h1>
            <h3>{schedule.title}</h3>
            <p>{schedule.content}</p>
      </div>
    );
};

export default ScheduleDetails;