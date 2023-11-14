package com.example.ec2test.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "board_idx")
    private BoardEntity board;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;

    private String content;
    private String author;

    @Column(name = "created_at")
    private Timestamp createdAt;

    private Integer heart;

    public void setHeart(Integer heart) {
        this.heart = heart;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
