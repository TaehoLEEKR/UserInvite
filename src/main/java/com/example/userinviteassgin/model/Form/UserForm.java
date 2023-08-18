package com.example.userinviteassgin.model.Form;


import lombok.*;
import com.example.userinviteassgin.model.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {

    @Email(message = "이메일 형식에 맞춰주세요")
    @NotNull(message = "이메일을 입력해주세요")
    private String userEmail;
    @NotNull(message = "이름을 입력해주세요")
    private String userName;
    private String password;
    @NotNull(message = "전화번호를 입력해주세요")
    private String phone;


    public  User toEntity(){
        return User.builder()
                .userEmail(userEmail)
                .userName(userName)
                .password(password)
                .phone(phone)
                .roles(Collections.singletonList("NOT_VALID_USER"))
                .build();
    }
}
