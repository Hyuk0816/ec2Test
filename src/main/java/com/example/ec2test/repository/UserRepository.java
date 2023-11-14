package com.example.ec2test.repository;



import com.example.ec2test.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email); // user table의 해당 email에 해당하는 data 추출


    boolean existsByEmail(String email);

    Optional<UserEntity> findByUserId(Long userId);

}