package service;

import exception.GlobalException;
import lombok.RequiredArgsConstructor;
import model.entity.User;
import model.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }
    private UserDetails createUserDetails(User user) {
        return User.builder()
                .userEmail(user.getUserEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRoles())
                .build();
    }
}
