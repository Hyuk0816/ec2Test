package com.example.ec2test.dto;


import com.example.ec2test.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class UserRequestDto {

    private String email;
    private String password;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .email(email)
                .password(password)
                .build();
    }

}
