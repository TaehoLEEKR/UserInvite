package com.example.userinviteassgin.service;


import com.example.userinviteassgin.client.mailgun.MailgunClient;
import com.example.userinviteassgin.client.mailgun.SendMailForm;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokensGenerator authTokensGenerator;
    private final MailgunClient mailgunClient;


    @Transactional
    public String userSignUp(UserForm userForm){
        if(userRepository.findByUserEmail(userForm.getUserEmail()).isPresent()){
            throw new GlobalException(Errorcode.ALREADY_EMAIL_USER);
        }
        String randomCode = getRandomCode();
        User user = userForm.toEntity();
        user.setPassword(passwordEncrypt(user.getPassword()));
        user.setVerificationCode(randomCode);
        SendMailForm sendMailForm = SendMailForm.builder()
                .from("ValidationMake@invite.com")
                .to(userForm.getUserEmail())
                .subjects("verificaiton Sign Up")
                .text(getMakeVerificaiton(userForm.getUserEmail(),userForm.getUserName(),randomCode))
                .build();
        mailgunClient.sendEmail(sendMailForm);
        userRepository.save(user);
        return "임시 회원가입에 성공하였습니다.";
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
    private String passwordEncrypt(String password){
        if(password.length() >= 8){
            return passwordEncoder.encode(password);
        }
        throw new GlobalException(Errorcode.TO_SHORT_PASSWORD);
    }
    @Transactional
    public String verifyEmail(String userEmail, String code) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(()->new GlobalException(Errorcode.NOT_CORRECT_EMAIL_USER));
        if (!user.getVerificationStatus() && user.getVerificationCode().equals(code)){
            user.setVerificationStatus(true);
            user.setRoles(Collections.singletonList("VALID_USER"));
            return user.getUserEmail() +" 님 인증에 성공하였습니다.";
        }else{
            return "인증에 실패 하였습니다.";
        }
    }

    private String getRandomCode(){return RandomStringUtils.random(10,true,true);}
    private String getMakeVerificaiton(String email, String name,String code){
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verificaiton.\n\n")
                .append("http://localhost:8080/api/users/verify/?userEmail=")
                .append(email)
                .append("&code=")
                .append(code).toString();

    }

}
