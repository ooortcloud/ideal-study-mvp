import React, { useState } from 'react';
import { createClass, updateClass } from '../api/apiClient';

const ClassroomForm = ({ initialData = null, onSuccess }) => {
  const [form, setForm] = useState(initialData || { title: '', description: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (form.id) {
      await updateClass(form.id, form);
    } else {
      await createClass(form);
    }
    onSuccess();
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="클래스 이름"
        value={form.title}
        onChange={(e) => setForm({ ...form, title: e.target.value })}
      />
      <textarea
        placeholder="클래스 설명"
        value={form.description}
        onChange={(e) => setForm({ ...form, description: e.target.value })}
      />
      <button type="submit">{form.id ? '수정하기' : '생성하기'}</button>
    </form>
  );
};

export default ClassroomForm;
