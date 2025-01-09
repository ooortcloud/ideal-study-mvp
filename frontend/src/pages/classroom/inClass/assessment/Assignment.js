import React, { useState, useEffect } from "react";
import AssignmentCreation from "../../../../components/classroom/inClass/assessment/AssessmentForm";
import AssignmentList from "../../../../components/classroom/inClass/assessment/AssessmentList";

const AssessmentPage = () => {
  const [assignments, setAssignments] = useState([]);

  useEffect(() => {
    const fetchAssignments = async () => {};
  });
  const handleCreateAssignment = (assignment) => {
    setAssignments([...assignments, { id: Date.now(), ...assignment }]);
  };

  const handleEditAssignment = (id) => {
    // Edit logic here
  };

  const handleDeleteAssignment = (id) => {
    setAssignments(assignments.filter((assignment) => assignment.id !== id));
  };

  return (
    <div>
      <h2>과제 관리</h2>
      <AssignmentCreation onCreate={handleCreateAssignment} />
      <AssignmentList
        assignments={assignments}
        onEdit={handleEditAssignment}
        onDelete={handleDeleteAssignment}
      />
    </div>
  );
};

export default AssessmentPage;
