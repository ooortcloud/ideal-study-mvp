import apiClient from "../apiClient.mjs";

/*
 * 자료 생성
 */
export const createMaterial = async (teacherId, classId, materials) => {
  console.log("자료 생성 시도:", { teacherId, classId, materials });
  try {
    const response = await apiClient.post(
      `/api/materials/${teacherId}`,
      materials,
      {
        headers: { "Content-Type": "multipart/form-data" },
      }
    );
    console.log("자료 생성 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("자료 생성 실패:", err);
    return {
      id: Math.random().toString(36).substr(2, 9),
      files: materials.files,
      description: materials.description,
      visibility: materials.visibility,
      uploadDate: new Date().toISOString(),
      teacherId,
      classId,
    };
  }
};

/*
 * 자료 상세 조회
 */
export const readMaterialById = async (materialId) => {
  console.log("자료 조회 시도:", materialId);
  try {
    const response = await apiClient.get(`/api/materials/${materialId}`);
    console.log("자료 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("자료 조회 실패:", err);
    return {
      id: materialId,
      title: "샘플 자료",
      description: "임시 자료입니다",
      fileUrl: "sample.pdf",
      uploadDate: new Date().toISOString(),
      visibility: "PUBLIC",
      fileSize: "2.5MB",
      downloadCount: 10,
    };
  }
};

/*
 * 특정학생용 자료 목록 조회
 */
export const readMaterialsByStudentId = async (
  classroomId,
  studentId,
  page = 1
) => {
  console.log("학생별 자료 조회 시도:", { classroomId, studentId, page });
  try {
    const response = await apiClient.get(
      `/api/materials/student/${classroomId}?studentId=${studentId}&page=${page}`
    );
    console.log("학생별 자료 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("학생별 자료 조회 실패:", err);
    return {
      content: Array(10)
        .fill()
        .map((_, i) => ({
          id: `dummy-${i}`,
          title: `샘플 자료 ${i + 1}`,
          description: `샘플 설명 ${i + 1}`,
          uploadDate: new Date().toISOString(),
          fileSize: "1.5MB",
          visibility: "INDIVIDUAL",
        })),
      totalPages: 5,
      currentPage: page,
      hasNext: page < 5,
    };
  }
};

/*
 * 공개 자료 목록 조회
 */
export const readMaterialsByClassId = async (classId, page = 1) => {
  console.log("클래스별 자료 조회 시도:", { classId, page });
  try {
    const response = await apiClient.get(
      `/materials/classroom/${classId}?page=${page}`
    );
    console.log("클래스별 자료 조회 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("클래스별 자료 조회 실패:", err);
    return {
      content: Array(10)
        .fill()
        .map((_, i) => ({
          id: `public-${i}`,
          title: `공개 자료 ${i + 1}`,
          description: `공개 자료 설명 ${i + 1}`,
          uploadDate: new Date().toISOString(),
          fileSize: "1.5MB",
          visibility: "PUBLIC",
        })),
      totalPages: 3,
      currentPage: page,
      hasNext: page < 3,
    };
  }
};

/*
 * 자료 수정
 */
export const updateMaterial = async (materialId, updateData) => {
  console.log("자료 수정 시도:", { materialId, updateData });
  try {
    const response = await apiClient.patch(
      `/api/materials/${materialId}`,
      updateData
    );
    console.log("자료 수정 성공:", response.data);
    return response.data;
  } catch (err) {
    console.error("자료 수정 실패:", err);
    return {
      ...updateData,
      id: materialId,
      updateDate: new Date().toISOString(),
    };
  }
};

/*
 * 자료 삭제
 */
export const deleteMaterial = async (materialId) => {
  console.log("자료 삭제 시도:", materialId);
  try {
    await apiClient.delete(`/api/materials/${materialId}`);
    console.log("자료 삭제 성공");
    return true;
  } catch (err) {
    console.error("자료 삭제 실패:", err);
    return false;
  }
};
