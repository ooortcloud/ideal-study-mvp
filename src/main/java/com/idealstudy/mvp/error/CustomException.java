package com.idealstudy.mvp.error;

import com.idealstudy.mvp.enums.error.ErrorCode;
import lombok.Getter;

/**
 * 원래 의도는 http 상태 코드를 변경하고자 했던 것인데, 웹 상에서는 이를 지원하지 않는다.
 * 그러면 각 커스텀 에러 별 상태코드를 포함하여 응답 본문을 하나의 JSON 형식으로 매번 전달해야 한다는 것인데.
 * 이렇게 커스텀하게 정의된 로직을 지금 당장 적용하기에는 전체 컨트롤러를 수정해야 하므로 시간이 많이 잡아먹을 것으로 예상됨.
 * 일단 히스토리는 남겨두고, 추가 기능으로 빼야 할듯.
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {

        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
