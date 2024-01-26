package com.example.pawsly.UserBoard;


import com.example.pawsly.Jwt.JwtTokenProvider;
import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
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
    //비밀글 제외 전체 게시물 출력
    public List<Board> getAllPosts() {
        List<Board> allPosts = boardRepository.findAll();
        List<Board> visiblePosts = new ArrayList<>(); // 비밀글은 제외

        if (allPosts != null) {
            for (Board board : allPosts) {
                if (board != null && board.getSecret() != null && !board.getSecret().equals("Y")) {
                    visiblePosts.add(board);
                }
            }
        }
        return visiblePosts;
    }
    //개인피드
    public List<Board> getPostsByUser(String accessToken) {
        String userKeyFromToken = jwtTokenProvider.extractUserKeyFromToken(accessToken);
        System.out.println(accessToken+"이건 추출한 토큰");
        System.out.println(userKeyFromToken);
        if (userKeyFromToken == null) {
            throw new RuntimeException("Invalid token or userKey not found in token.");
        }

        List<Board> userBoards = boardRepository.findByWriter(userKeyFromToken);

        return userBoards;
    }

    // 게시물 작성
    public Board createPost(Board board, String accessToken) {
        String userKeyFromToken = jwtTokenProvider.extractUserKeyFromToken(accessToken);
        System.out.println("게시물작성:"+accessToken);
        if (userKeyFromToken == null) {
            throw new RuntimeException("Invalid token or userKey not found in token.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated.");
        }

        User user = userRepository.findByUserKey(userKeyFromToken);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        board.setWriter(userKeyFromToken);
        board.setNickname(user.getNickname());
        board.setCreatedBd(LocalDateTime.now());

        Board createdBoard = boardRepository.save(board); // 저장 후 생성된 board 반환

        return createdBoard;
    }


    // 게시물 수정
    public Board updatePost(String boardKey, Board updatedBoard, String accessToken) {
        String userKeyFromToken = jwtTokenProvider.extractUserKeyFromToken(accessToken);
        if (userKeyFromToken == null) {
            throw new RuntimeException("Invalid token or userKey not found in token.");
        }

        Optional<Board> existingBoardOptional = boardRepository.findById(boardKey);
        if (existingBoardOptional.isEmpty()) {
            throw new RuntimeException("Board not found.");
        }

        Board existingBoard = existingBoardOptional.get();
        if (!existingBoard.getWriter().equals(userKeyFromToken)) {
            throw new RuntimeException("You don't have permission to update this post.");
        }

        // 업데이트할 필드만 설정
        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setContent(updatedBoard.getContent());
        existingBoard.setLastModifiedBd(LocalDateTime.now()); // 수정된 부분

        return boardRepository.save(existingBoard);
    }

    public void deletePost(String boardKey, String accessToken) {
        String userKeyFromToken = jwtTokenProvider.extractUserKeyFromToken(accessToken);
        if (userKeyFromToken == null) {
            throw new RuntimeException("Invalid token or userKey not found in token.");
        }

        Optional<Board> existingBoardOptional = boardRepository.findById(boardKey);
        if (existingBoardOptional.isEmpty()) {
            throw new RuntimeException("Board not found.");
        }

        Board existingBoard = existingBoardOptional.get();
        if (!existingBoard.getWriter().equals(userKeyFromToken)) {
            throw new RuntimeException("You don't have permission to delete this post.");
        }

        boardRepository.delete(existingBoard);
    }




}



