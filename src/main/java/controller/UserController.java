package controller;

import lombok.RequiredArgsConstructor;
import model.Form.UserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/sign-up")
    public ResponseEntity<String> userSignUp(@RequestBody @Valid UserForm userForm){
        return ResponseEntity.ok("");
    }
}
