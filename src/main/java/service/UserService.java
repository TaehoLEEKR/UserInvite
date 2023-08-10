package service;

import exception.Errorcode;
import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import model.Form.UserForm;
import model.entity.User;
import model.repository.UserRepository;
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

}
