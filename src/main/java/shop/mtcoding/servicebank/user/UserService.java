package shop.mtcoding.servicebank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.servicebank._core.erros.exception.Exception400;
import shop.mtcoding.servicebank._core.erros.exception.Exception401;
import shop.mtcoding.servicebank._core.security.JwtTokenProvider;
import shop.mtcoding.servicebank._core.security.MyUserDetails;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional // Springframework Transaction (주의)
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO joinDTO) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinDTO.getUsername());
        if (userOP.isPresent()) {
            throw new Exception400("username", "동일한 유저네임이 존재합니다");
        }

        // 2. 회원가입
        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        User userPS = userRepository.save(joinDTO.toEntity());

        // 3. DTO 응답
        return new UserResponse.JoinDTO(userPS);
    }

    @Transactional(readOnly = true) // 변경감지 하지 않음
    public String 로그인(UserRequest.LoginDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            return JwtTokenProvider.create(myUserDetails.getUser());
        }catch (Exception e){
            throw new Exception401("인증되지 않았습니다");
        }
    }
}
