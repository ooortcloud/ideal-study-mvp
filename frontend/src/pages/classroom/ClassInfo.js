import React from 'react';

const ClassInfo = ({ classroom }) => {
  return (
    <div className="class-info">
      <img src={classroom.thumbnail} alt={`${classroom.title} 썸네일`} />
      <h1>{classroom.title}</h1>
      <p>{classroom.description}</p>
      <p>모집 인원: {classroom.capacity}명</p>
    </div>
  );
};

export default ClassInfo;