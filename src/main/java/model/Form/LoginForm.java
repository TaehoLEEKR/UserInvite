package model.Form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {

    @Email(message = "이메일 형식에 맞춰주세요.")
    @NotNull(message = "이메일이 입력해주세요.")
    private String userEmail;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
