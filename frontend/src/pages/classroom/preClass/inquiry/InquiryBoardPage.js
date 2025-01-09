import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getInquiriesByClassId } from "../../../../services/classroom/InquiryService.mjs";
import Pagination from "../../../../components/Pagination";
import InquiryList from "../../../../components/classroom/preClass/inquiry/InquiryList";
import InquiryHeader from "../../../../components/classroom/preClass/inquiry/InquiryHeader";

const InquiryBoard = () => {
  const navigate = useNavigate();
  const { classId } = useParams();
  const [inquiries, setInquiries] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetchInquiries = async () => {
      const data = await getInquiriesByClassId(classId);
      setInquiries(data.inquiries);
      setTotalPages(data.pagination.totalPages);
    };
    fetchInquiries();
  }, [classId, currentPage]);

  const handleInquiryClick = (inquiryId) => {
    navigate(`/classes/${classId}/inquiries/${inquiryId}`);
  };

  const handleNewInquiry = () => {
    navigate(`/classes/${classId}/inquiries/new`);
  };

  return (
    <div className="inquiry-board">
      <InquiryHeader classId={classId} onNewInquiry={handleNewInquiry} />
      <InquiryList
        inquiries={inquiries}
        classId={classId}
        onInquiryClick={handleInquiryClick}
      />
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={setCurrentPage}
      />
    </div>
  );
};

export default InquiryBoard;
