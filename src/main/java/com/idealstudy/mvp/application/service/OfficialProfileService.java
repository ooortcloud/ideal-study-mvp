package com.idealstudy.mvp.application.service;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.OfficialProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfficialProfileService {

    @Autowired
    private final OfficialProfileRepository officialProfileRepository;

    public void create() {

    }

    public OfficialProfileDto selectOne(String userId) {

        return null;
    }

    public OfficialProfileDto update(String userId) {

        return selectOne(userId);
    }
}
