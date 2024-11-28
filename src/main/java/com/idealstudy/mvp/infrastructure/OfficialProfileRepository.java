package com.idealstudy.mvp.infrastructure;


import com.idealstudy.mvp.application.dto.OfficialProfileDto;

public interface OfficialProfileRepository {

    void create(String teacherId);

    OfficialProfileDto findByTeacherId(String teacherId);

    void update(String teacherId, String contents);
}
