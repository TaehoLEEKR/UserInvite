package com.example.userinviteassgin.service;

import com.example.userinviteassgin.exception.Errorcode;
import com.example.userinviteassgin.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import com.example.userinviteassgin.model.Form.LoginForm;
import com.example.userinviteassgin.model.Form.UserForm;
import com.example.userinviteassgin.model.entity.User;
import com.example.userinviteassgin.model.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String userSignUp(UserForm userForm){
        if(userRepository.findByUserEmail(userForm.getUserEmail()).isPresent()){
            throw new GlobalException(Errorcode.ALREADY_EMAIL_USER);
        }
        User user = userForm.toEntity();
        user.setPassword(passwordEncrypt(user.getUserName()));
        userRepository.save(user);
        return "회원가입에 성공하였습니다.";
    }
    public String passwordEncrypt(String password){
        if(password.length() >= 8){
            return passwordEncoder.encode(password);
        }
        throw new GlobalException(Errorcode.TO_SHORT_PASSWORD);
    }

    public Boolean userSignIn(LoginForm loginForm){
        User user = userRepository.findByUserEmail(loginForm.getUserEmail())
                .orElseThrow(()->new GlobalException(Errorcode.NOT_FIND_EMAIL_USER));

        if(!user.getUserEmail().equals(loginForm.getUserEmail())){
            throw new GlobalException(Errorcode.NOT_CORRECT_EMAIL_USER);
        } else if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new GlobalException(Errorcode.NOT_CORRECT_PASSWORD_USER);
        }else{
            return true;
        }
    }
}
