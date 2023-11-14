package com.example.ec2test.service;


import com.example.ec2test.entity.BoardEntity;
import com.example.ec2test.entity.UserEntity;
import com.example.ec2test.repository.BoardRepository;
import com.example.ec2test.repository.UserRepository;
import com.example.ec2test.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    public String updateBoard(Long boardId, BoardDto boardDto, String email) {
        Optional<BoardEntity> board = boardRepository.findById(boardId);
        if(board.isPresent()){
            if (board.get().getUser().getEmail().equals(email)) {
                board.get().update(boardDto.getTitle(), boardDto.getContent());
                boardRepository.save(board.get());
            return "수정 완료되었습니다.";
          }else return "다른 유저의 게시글입니다.";
        }else return "없는 게시글입니다.";
    }


    public BoardEntity createBoard(BoardDto boardDto, String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            BoardEntity board = BoardEntity.builder()
                                            .createdAt(currentTimestamp)
                                            .title(boardDto.getTitle())
                                            .content(boardDto.getContent())
                                            .email(email)
                                            .author(boardDto.getAuthor())
                                            .user(user.get())
                                            .build();
            return boardRepository.save(board);
        }else return null;
    }
}
