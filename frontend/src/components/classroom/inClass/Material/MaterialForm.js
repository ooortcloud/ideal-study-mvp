import React, { useState, useCallback } from "react";
import { useDropzone } from "react-dropzone";
import { createMaterial } from "../../../../services/classroom/MaterialService.mjs";
import styles from "./MaterialForm.module.css";

const MaterialForm = (classId) => {
  const [files, setFiles] = useState([]);
  const [description, setDescription] = useState("");
  const [visibility, setVisibility] = useState("PUBLIC");

  /*
  useCallback 사용하는 이유 
  - useDropzone은 내부적으로 여러 이벤트 리스너를 사용
  - onDrop 콜백이 변경될 때마다 이러한 리스너들이 다시 바인딩되는 것을 방지
  */
  const onDrop = useCallback((acceptedFile) => {
    // pdf 파일만 허용
    const pdfFile = acceptedFile.filter(
      (file) => file.type === "application/pdf"
    );
    setFiles(pdfFile);
  }, []);

  // getRootProps : drag and drop 구현을 위한 이벤트 헨들러 제공. 드랍존 root 태그에 props 들을 전달
  // getInputProps : 실제 파일 input 을 위한 props 제공
  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: "application/pdf",
    maxSize: 50 * 1024 * 1024, // 50MB
  });

  // 서버에 자료 업로드
  const handleSubmit = (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("files", new Blob( files[0], { type: "application/pdf"} ), files[0].name); // TODO : 여러 개의 파일 업로드 처리
    const materialData = JSON.stringify({
        classroomId : classId,
        description : description,
        status : visibility,
    });

    formData.append("dto", new Blob([materialData], {type: "application/json"}));
    // FormData의 내용을 확인하기 위한 디버깅 코드
    for (let pair of formData.entries()) {
      console.log(pair[0], pair[1]);
    }

    createMaterial("1", "2", formData);
  };

  return (
    <form onSubmit={handleSubmit} className={styles.form}>
      <div
        {...getRootProps()}
        className={`${styles.dropzone} ${isDragActive ? styles.active : ""}`}
      >
        <input {...getInputProps()} />
        <p className={styles.dropzoneText}>
          PDF 파일을 드래그하거나 클릭하여 업로드하세요
        </p>
      </div>

      {files.length > 0 && (
        <div className={styles.fileList}>
          {files.map((file, index) => (
            <div key={index} className={styles.fileItem}>
              <span className={styles.fileName}>{file.name}</span>
              <span className={styles.fileSize}>
                {(file.size / 1024 / 1024).toFixed(2)}MB
              </span>
              <button
                type="button"
                onClick={() => setFiles(files.filter((_, i) => i !== index))}
                className={styles.deleteButton}
              >
                삭제
              </button>
            </div>
          ))}
        </div>
      )}

      <textarea
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        placeholder="자료 설명"
        className={styles.description}
      />

      <select
        value={visibility}
        onChange={(e) => setVisibility(e.target.value)}
        className={styles.select}
      >
        <option value="PUBLIC">전체공개</option>
        <option value="INDIVIDUAL">개별공개</option>
      </select>

      <button type="submit" className={styles.submitButton}>
        업로드
      </button>
    </form>
  );
};

export default MaterialForm;
