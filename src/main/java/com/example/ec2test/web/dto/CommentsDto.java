package com.example.ec2test.web.dto;

import com.example.ec2test.entity.CommentsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentsDto {
    @JsonProperty("postId")
    private Long postId;

    private String content;

    private String author;

    @JsonProperty("heart")
    private Integer heart;

    @JsonProperty("boardId")
    private Long boardId;

    @JsonProperty("createdAt")
    private Timestamp createdAt;


    public static CommentsDto fromEntity(CommentsEntity entity) {
        CommentsDto dto = new CommentsDto();
        dto.setPostId(entity.getPostId());
        dto.setContent(entity.getContent());
        dto.setAuthor(entity.getAuthor());
        dto.setBoardId(entity.getBoard().getBoardId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setHeart(entity.getHeart());
        return dto;
    }
}
