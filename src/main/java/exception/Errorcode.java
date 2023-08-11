package exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Errorcode {
    ALREADY_EMAIL_USER("이미 존재하는 이메일 입니다."),
    NOT_FIND_EMAIL_USER("존재 하지 않는 이메일 입니다."),
    NOT_CORRECT_EMAIL_USER("이메일이 일치 하지 않습니다"),
    NOT_CORRECT_PASSWORD_USER("패스워드가 일치 하지 않습니다"),
    TO_SHORT_PASSWORD("패스워드의 길이가 너무 짧습니다 8자 이상 작성해주세요.")
    ;
    private final String MESSAGE;
}
