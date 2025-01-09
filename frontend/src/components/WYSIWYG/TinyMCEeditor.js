// temporary

import React, { useRef } from "react";
import { Editor } from "@tinymce/tinymce-react";

const TinymceEditor = ({ onChange }) => {
  const editorRef = useRef(null);
  const log = () => {
    if (editorRef.current) {
      console.log(editorRef.current.getContent());
    }
  };

  const handleEditorChange = (content) => {
    // TinyMCE의 내용을 부모 컴포넌트로 전달
    onChange(content);
  };

  const tinymcePlugins = ["link", "lists", "autoresize"];
  const tinymceToolbar =
    "blocks fontfamily |" +
    "bold italic underline strikethrough forecolor backcolor |" +
    "alignleft aligncenter alignright alignjustify |" +
    "bullist numlist blockquote link";

  return (
    <Editor
      apiKey={process.env.REACT_APP_TinyMCE_API_KEY}
      onInit={(e, editor) => (editorRef.current = editor)}
      init={{
        plugins: tinymcePlugins,
        toolbar: tinymceToolbar,
        min_height: 500,
        menubar: false,
        branding: false,
        statusbar: false,
        block_formats: "제목1=h2;제목2=h3;제목3=h4;본문=p;",
      }}
      onEditorChange={handleEditorChange} // onEditorChange 사용
    />
  );
};

export default TinymceEditor;
