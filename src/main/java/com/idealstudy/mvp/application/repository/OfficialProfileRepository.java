package com.idealstudy.mvp.application.repository;


import com.idealstudy.mvp.application.dto.OfficialProfileDto;

public interface OfficialProfileRepository {

    void create(String teacherId);

    OfficialProfileDto findByTeacherId(String teacherId);

    OfficialProfileDto update(String teacherId, String html);
}
