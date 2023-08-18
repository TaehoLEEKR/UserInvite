package com.example.userinviteassgin.service;


import com.example.userinviteassgin.component.AuthTokensGenerator;
import com.example.userinviteassgin.component.JwtToken;
import com.example.userinviteassgin.component.JwtTokenProvider;
import com.example.userinviteassgin.exception.Errorcode;
import com.example.userinviteassgin.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import com.example.userinviteassgin.model.Form.LoginForm;
import com.example.userinviteassgin.model.Form.UserForm;
import com.example.userinviteassgin.model.entity.User;
import com.example.userinviteassgin.model.repository.UserRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokensGenerator authTokensGenerator;


    @Transactional
    public String userSignUp(UserForm userForm){
        if(userRepository.findByUserEmail(userForm.getUserEmail()).isPresent()){
            throw new GlobalException(Errorcode.ALREADY_EMAIL_USER);
        }
        User user = userForm.toEntity();
        user.setPassword(passwordEncrypt(user.getPassword()));
        userRepository.save(user);
        return "회원가입에 성공하였습니다.";
    }
    public String passwordEncrypt(String password){
        if(password.length() >= 8){
            return passwordEncoder.encode(password);
        }
        throw new GlobalException(Errorcode.TO_SHORT_PASSWORD);
    }

    @Transactional
    public JwtToken userSignIn(LoginForm loginForm){
        User user = userRepository.findByUserEmail(loginForm.getUserEmail())
                .orElseThrow(()->new GlobalException(Errorcode.NOT_FIND_EMAIL_USER));

        if(!user.getUserEmail().equals(loginForm.getUserEmail())){
            throw new GlobalException(Errorcode.NOT_CORRECT_EMAIL_USER);
        } else if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new GlobalException(Errorcode.NOT_CORRECT_PASSWORD_USER);
        }else{
            return authTokensGenerator.generate(user.getUserId(), user.getRoles());
        }
    }
}
