package com.idealstudy.mvp.enums.member;

public enum Gender {

    // 이거 다 순수 영어 string으로 변경할 것
    MALE("남"),
    FEMALE("여"),
    OTHER("기타");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGenderStr() {
        return gender;
    }

    public static Gender stringToEnum(String gender) {
        for(Gender val : Gender.values())
            if(val.gender.equals(gender))
                return val;

        throw new IllegalArgumentException("알 수 없는 성별");
    }
}
