package com.example.ec2test.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "heart")
public class HeartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_idx")
    private Long heartId;

    @ManyToOne
    @JoinColumn(name = "post_idx")
    private CommentsEntity comments;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;


    public HeartEntity(CommentsEntity comments, UserEntity user) {
        this.comments = comments;
        this.user = user;
    }
}
