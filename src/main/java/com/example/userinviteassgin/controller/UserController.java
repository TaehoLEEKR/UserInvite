package com.example.userinviteassgin.controller;


import com.example.userinviteassgin.component.JwtToken;
import lombok.RequiredArgsConstructor;
import com.example.userinviteassgin.model.Form.LoginForm;
import com.example.userinviteassgin.model.Form.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userinviteassgin.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<String> userSignUp(@RequestBody @Valid UserForm userForm){
        return ResponseEntity.ok(userService.userSignUp(userForm));
    }
    @PostMapping("/sign-in")
    public ResponseEntity<JwtToken> userSignIn(@RequestBody @Valid LoginForm loginForm){
        return ResponseEntity.ok(userService.userSignIn(loginForm));
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verificationUser(@RequestParam String userEmail, @RequestParam String code){
        log.error("userEmail = "+userEmail +"code = " + code);
        log.info("test");
        return ResponseEntity.ok(userService.verifyEmail(userEmail,code));
    }
}
