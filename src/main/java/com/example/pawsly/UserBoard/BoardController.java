package com.example.pawsly.UserBoard;

import com.example.pawsly.Jwt.JwtTokenProvider;
import com.example.pawsly.User.User;
import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardRepository boardRepository, JwtTokenProvider jwtTokenProvider, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.boardService= boardService;
    }
    //전체피드
    @GetMapping("/list")
    public List<Board> getAllPosts() {
        System.out.println("전체피드 출력 완료");
        return boardService.getAllPosts();
    }

    //개인 피드
    @GetMapping("/list/writer")
    public List<Board> getUserPostsByWriter(@RequestHeader("Authorization") String accessToken) {
        System.out.println(accessToken); // authToken 값 출력하여 확인

        String tokenPrefix = "Bearer ";
        if (accessToken != null && accessToken.startsWith(tokenPrefix)) {
            String token = accessToken.substring(tokenPrefix.length()); // 접두사 "Bearer " 제거
            System.out.println("Extracted Token: " + token);

            return boardService.getPostsByUser(token); // 토큰 값 전달하여 처리
        } else {
            throw new RuntimeException("Invalid Authorization header format");
        }
    }


    //게시물 작성
    @PostMapping("/post")
    public ResponseEntity<Board> createPost(@RequestBody Board board, @RequestHeader("Authorization") String accessToken) {
        String tokenPrefix = "Bearer ";
        if (accessToken != null && accessToken.startsWith(tokenPrefix)) {
            String token = accessToken.substring(tokenPrefix.length()); // 접두사 "Bearer " 제거

            Board createdBoard = boardService.createPost(board, token); // 토큰 값 전달하여 처리
            System.out.println("게시물 작성 완료");
            return ResponseEntity.ok(createdBoard);
        } else {
            throw new RuntimeException("Invalid Authorization header format");
        }
    }

    // 게시물 수정
    @PutMapping("/post/{boardKey}")
    public ResponseEntity<Board> updatePost(
            @PathVariable String boardKey,
            @RequestBody Board updatedBoard,
            @RequestHeader("Authorization") String authToken) {

        Board updatedPost = boardService.updatePost(boardKey, updatedBoard, authToken);
        System.out.println("게시물 수정 완료");
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/post/{boardKey}")
    public ResponseEntity<String> deletePost(
            @PathVariable String boardKey,
            @RequestHeader("Authorization") String accessToken) {

        boardService.deletePost(boardKey, accessToken);
        System.out.println("게시물 삭제 완료");
        return ResponseEntity.ok("Post deleted successfully");
    }




}