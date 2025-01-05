package com.idealstudy.mvp.application.repository;


import com.idealstudy.mvp.application.dto.OfficialProfileDto;

public interface OfficialProfileRepository {

    OfficialProfileDto create(String teacherId, String initContent);

    OfficialProfileDto findByTeacherId(String teacherId);

    OfficialProfileDto update(String teacherId, String html);
}
