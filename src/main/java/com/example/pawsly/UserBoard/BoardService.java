package com.example.pawsly.UserBoard;


import com.example.pawsly.Jwt.JwtTokenProvider;
import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public PostDto createPost(PostDto postDto, String authToken) {
        String userKeyFromToken = jwtTokenProvider.extractUserKeyFromToken(authToken);
        if (userKeyFromToken == null) {
            throw new RuntimeException("Invalid token or userKey not found in token.");
        }

        // Authentication 객체를 통해 현재 로그인한 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated.");
        }

        // UserKey로부터 User 정보를 가져옴
        User user = userRepository.findByUserKey(userKeyFromToken);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        // 현재 로그인한 사용자의 userKey
        postDto.setWriter(userKeyFromToken);  // 작성자 정보 설정
        postDto.setNickname(user.getNickname());  // 닉네임 설정
        postDto.setCreatedBd(LocalDateTime.now());

        // 게시물 저장
        Board board = new Board();
        board.setTitle(postDto.getTitle());
        board.setContent(postDto.getContent());
        board.setNickname(postDto.getNickname());
        board.setSecret(postDto.getSecret());
        board.setBoardState(postDto.getBoardState());
        board.setWriter(postDto.getWriter());
        board.setCreatedBd(postDto.getCreatedBd());
        boardRepository.save(board);

        return postDto;
    }
}



