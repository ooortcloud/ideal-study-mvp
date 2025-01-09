import React from "react";
import Button from "./Button";

const Pagination = ({ currentPage, totalPage, onPageChange }) => {
  return (
    <div style={{ display: "flex", flexDirection: "row", gap: 5 }}>
      {/* 이전 버튼 */}
      <Button
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 1}
      >
        이전
      </Button>

      {/* 페이지 버튼 */}
      {Array.from({ length: 5 }, (_, index) => (
        <Button key={index} onClick={() => onPageChange(index + 1)}>
          {index + 1}
        </Button>
      ))}

      {/* 다음 버튼 */}
      <Button
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPage}
      >
        다음
      </Button>
    </div>
  );
};

export default Pagination;
