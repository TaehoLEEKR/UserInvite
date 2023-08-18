package com.example.userinviteassgin.component.filter;

import com.example.userinviteassgin.exception.Errorcode;
import com.example.userinviteassgin.exception.GlobalException;
import com.example.userinviteassgin.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserEmail(username)
                .orElseThrow(() -> new GlobalException(Errorcode.NOT_FIND_EMAIL_USER));
    }
}
