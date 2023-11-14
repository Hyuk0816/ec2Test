package com.example.ec2test.web.dto;


import com.example.ec2test.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private UserEntity user;
    private String email;
    private String title;
    private String author;

    private String content;
}
