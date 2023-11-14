package com.example.ec2test.service;




import com.example.ec2test.dto.UserRequestDto;
import com.example.ec2test.entity.UserEntity;
import com.example.ec2test.exception.NotFoundException;
import com.example.ec2test.jwt.JwtTokenProvider;
import com.example.ec2test.repository.UserRepository;
import com.example.ec2test.role.Role;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String,String> redisTemplate;



    @Transactional
    public String signUp(UserRequestDto userDto) {

        String email = userDto.getEmail();
        String password = userDto.getPassword();
        //1. 중복 이메일 가입 확인
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 가입된 메일 입니다");
        }
        //2 회원 저장
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "회원가입이 완료 되었습니다.";
    }
    @Transactional
    public String login(UserRequestDto loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            userRepository.findByEmail(email)
                    .orElseThrow(() -> new NotFoundException("회원이 없습니다"));

            if(redisTemplate.opsForValue().get("logout: " + loginRequest.getEmail()) != null){
                redisTemplate.delete("logout: " + loginRequest.getEmail());
            }

            return jwtTokenProvider.createToken(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("잘못된 자격증명입니다");
        }
    }


    @Transactional
    public void logout(@RequestBody UserRequestDto userRequestDto) {
        //Token에서 로그인한 사용자 정보 get해 로그아웃 처리

        if (redisTemplate.opsForValue().get(userRequestDto.getEmail()) != null) {
            redisTemplate.opsForValue().set("logout: " + userRequestDto.getEmail(), "logout");
            redisTemplate.delete(userRequestDto.getEmail()); //Token 삭제
        }
    }
    //로그아웃 테스트용 api --> 토큰 유효성 검사
    public boolean test(@RequestBody UserRequestDto userRequestDto){

        if(redisTemplate.opsForValue().get("logout: " + userRequestDto.getEmail()) != null){
            return false;
        }
        else{
            System.out.println("정상적인 key ");
        }
        return true;
    }

    public boolean test2(String email){

        if(redisTemplate.opsForValue().get("logout: " + email) != null){
            return false;
        }
        else{
            System.out.println("정상적인 key ");
        }
        return true;
    }


}
