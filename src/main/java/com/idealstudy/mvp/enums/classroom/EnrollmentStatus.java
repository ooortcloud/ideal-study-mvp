package com.idealstudy.mvp.enums.classroom;

/**
 * Request: 학부모/학생이 강사의 수업을 듣기위해 신청을 한 상태 (인강 볼 수 없음)
 * Checked: 강사가 수락하여 결제를 기다리고 있는 상태 (인강 볼수 없음)
 * Permitted: 수업에 등록되어 수강생이 된 상태 (인강 볼 수 있음)
 * Expired: 수업기간이 끝나서 수업참여가 종료된 상태 (인강 볼 수 없음)
 */
public enum EnrollmentStatus {

    REQUEST,
    CHECKED,
    PERMITTED,
    EXPIRED;
}
