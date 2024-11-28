import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './ClassroomCard.module.css';

const ClassroomCard = ({ classroom }) => {
  const navigate = useNavigate();

  return (
    <div className={styles['classroom-card']}>
      <h3>{classroom.title}</h3>
      <p>{classroom.description}</p>
      <button onClick={() => navigate(`/classes/${classroom.id}`)}>상세보기</button>
    </div>
  );
};

export default ClassroomCard;