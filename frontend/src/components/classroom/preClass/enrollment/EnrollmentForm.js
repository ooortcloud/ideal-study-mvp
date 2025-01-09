import React, { useState, useEffect, useContext } from "react";
import {
  createEnrollment,
  readEnrollment,
} from "../../../../services/classroom/EnrollmentService.mjs";
import { AuthContext } from "../../../../context/AuthContext";
import Button from "../../../Button";

const EnrollmentForm = ({ classId, isClose }) => {
  const { userInfo } = useContext(AuthContext);

  const [formData, setFormData] = useState({
    curScore: "",
    targetScore: "",
    request: "",
    determination: "",
  });

  // 수업신청이력이 있다면 정보가져오기
  useEffect(() => {
    const fetchEnrollment = async () => {
      const data = await readEnrollment({ classId, userInfo });
      setFormData(data);
    };
    fetchEnrollment();
  }, [classId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      classroomId: classId,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(formData);
    await createEnrollment(formData);
    alert("수업신청이 완료되었습니다");
    isClose();
  };

  return (
    <div className="enrollment-form">
      <h3>수업 신청</h3>
      <form onSubmit={handleSubmit}>
        <div>
          <label>현재 성적</label>
          <input
            type="text"
            name="curScore"
            value={formData.curScore}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>목표 성적</label>
          <input
            type="text"
            name="targetScore"
            value={formData.targetScore}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>고민되는 점, 선생님께 바라는 점</label>
          <textarea
            name="request"
            value={formData.request}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>요청사항 및 한마디</label>
          <textarea
            name="determination"
            value={formData.determination}
            onChange={handleChange}
          />
        </div>
        <br></br>
        <Button type="submit"> 신청 </Button>
        <Button onClick={isClose}> 나중에 </Button>
      </form>
    </div>
  );
};

export default EnrollmentForm;
