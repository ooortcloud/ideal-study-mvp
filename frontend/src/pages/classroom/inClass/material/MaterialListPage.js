import React from "react";
import "./MaterialListPage.css";
import Button from "../../../../components/Button";
import MaterialForm from "../../../../components/classroom/inClass/Material/MaterialForm";
const MaterialListPage = ({classId}) => {
  return (
    <div>
      <div className="materialListPageHeader">
        <h2>자료 목록</h2>
        <Button>자료 추가</Button>
      </div>
      <MaterialForm classId = {classId} />
      <div className="materialListPageContent">
        <div className="materialListPageItem">
          <h3>제목</h3>
          <p>설명</p>
        </div>
        <div className="materialListPageFooter">
          <Button>자료 다운로드</Button>
          <Button>자료 삭제</Button>
          <Button>자료 수정</Button>
          <Button>자료 공유</Button>
        </div>
      </div>
    </div>
  );
};

export default MaterialListPage;
