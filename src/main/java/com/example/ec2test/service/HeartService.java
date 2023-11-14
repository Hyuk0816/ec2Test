package com.example.ec2test.service;


import com.example.ec2test.entity.CommentsEntity;
import com.example.ec2test.entity.HeartEntity;
import com.example.ec2test.entity.UserEntity;
import com.example.ec2test.repository.CommentsRepository;
import com.example.ec2test.repository.HeartRepository;
import com.example.ec2test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    public ResponseEntity<String> clickHeart(Long postId, String email) {
        Optional<CommentsEntity> comments = commentsRepository.findByPostId(postId);
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if(comments.isPresent() && user.isPresent()) {
            if(heartRepository.findByUser(user.get()).isEmpty()) {
                setHeart(comments.get(), user.get(), comments.get().getHeart(), true);
                return ResponseEntity.ok().body(postId + "번 댓글에 좋아요가 추가되었습니다.");
            }
            else {
                setHeart(comments.get(),user.get(), comments.get().getHeart(), false);
                return ResponseEntity.ok().body(postId + "번 댓글에 좋아요가 삭제되었습니다.");
            }
        }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
    }

    public void setHeart(CommentsEntity comments, UserEntity user, Integer heartCount, Boolean message){
        if(message){
            HeartEntity heart = new HeartEntity(comments, user);
            heartCount++;
            comments.setHeart(heartCount);
            heartRepository.save(heart);
            commentsRepository.save(comments);
        }else {
            heartRepository.deleteByUser(user);
            heartCount--;
            comments.setHeart(heartCount);
            commentsRepository.save(comments);
        }
    }

}
