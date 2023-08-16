package service;

import common.JwtTokenInfo;
import common.JwtTokenProvider;
import exception.Errorcode;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import model.Form.LoginForm;
import model.Form.UserForm;
import model.entity.User;
import model.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
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

    public JwtTokenInfo userSignIn(LoginForm loginForm){
        User user = userRepository.findByUserEmail(loginForm.getUserEmail())
                .orElseThrow(()->new GlobalException(Errorcode.NOT_FIND_EMAIL_USER));

        if(!user.getUserEmail().equals(loginForm.getUserEmail())){
            throw new GlobalException(Errorcode.NOT_CORRECT_EMAIL_USER);
        } else if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new GlobalException(Errorcode.NOT_CORRECT_PASSWORD_USER);
        }else{
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginForm.getUserEmail(),loginForm.getPassword());

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            JwtTokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            return tokenInfo;
        }
    }
}
