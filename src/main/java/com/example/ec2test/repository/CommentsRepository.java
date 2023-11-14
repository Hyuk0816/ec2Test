package com.example.ec2test.repository;

import com.example.ec2test.entity.BoardEntity;
import com.example.ec2test.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    Optional<CommentsEntity> findByPostId(Long postId);


    List<CommentsEntity> findByBoard(BoardEntity board);
}
