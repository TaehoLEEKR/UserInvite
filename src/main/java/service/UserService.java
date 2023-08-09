package service;

import lombok.RequiredArgsConstructor;
import model.Form.UserForm;
import model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String userSignUp(UserForm userForm){
        return "";
    }
    public void checkUserEmail(String email){

    }
    public String passwordEncrypt(String passoword){
        return "";
    }

}
