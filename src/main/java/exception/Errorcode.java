package exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Errorcode {
    ALREADY_EMAIL_USER("이미 존재하는 이메일 입니다."),
    TO_SHORT_PASSWORD("패스워드의 길이가 너무 짧습니다 8자 이상 작성해주세요.")
    ;
    private final String MESSAGE;
}
