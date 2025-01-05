package com.idealstudy.mvp.application.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MemberService와 repository는 공유하지만, 기능 자체는 분리하기 위해 다른 클래스 영역에서 개발함.
 */
@Service
@Transactional
public class MyPageService {

}
