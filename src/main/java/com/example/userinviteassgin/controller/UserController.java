package com.example.userinviteassgin.controller;

import lombok.RequiredArgsConstructor;
import com.example.userinviteassgin.model.Form.LoginForm;
import com.example.userinviteassgin.model.Form.UserForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.userinviteassgin.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> userSignUp(@RequestBody @Valid UserForm userForm){
        return ResponseEntity.ok(userService.userSignUp(userForm));
    }

    @PostMapping("/sign-in")

    public ResponseEntity<Boolean> userSignIn(@RequestBody @Valid LoginForm loginForm){
        return ResponseEntity.ok(userService.userSignIn(loginForm));
    }
}
