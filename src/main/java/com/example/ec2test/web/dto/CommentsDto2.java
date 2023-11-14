package com.example.ec2test.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto2 {
    private String content;

    private String author;

    private Long boardId;

    private Long userId;
}
