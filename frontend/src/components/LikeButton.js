import React, { useState } from 'react';
import './LikeButton.css';

const LikeButton = () => {
  const [likes, setLikes] = useState(0);

  const handleLike = () => setLikes((prev) => prev + 1);

  return (
    <button className="like-button" onClick={handleLike}>
      좋아요 {likes}
    </button>
  );
};

export default LikeButton;