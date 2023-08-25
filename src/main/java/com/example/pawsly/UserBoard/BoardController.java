package com.example.pawsly.UserBoard;

import com.example.pawsly.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return boardService.getAllPosts();
    }

    //개인 피드
    @GetMapping("/list/writer")
    public List<Board> getUserPostsByWriter(@RequestHeader("Authorization") String authToken) {
        System.out.println(authToken);
        return boardService.getPostsByUser(authToken); // 메서드명 변경
    }

    //게시물 작성
    @PostMapping("/post")
    public ResponseEntity<Board> createPost(@RequestBody Board board, @RequestHeader("Authorization") String authToken) {
        System.out.println("Received Authorization Header: " + authToken);
        Board createdBoard = boardService.createPost(board, authToken);
        return ResponseEntity.ok(createdBoard);
    }

    // 게시물 수정
    @PutMapping("/post/{boardKey}")
    public ResponseEntity<Board> updatePost(
            @PathVariable String boardKey,
            @RequestBody Board updatedBoard,
            @RequestHeader("Authorization") String authToken) {

        Board updatedPost = boardService.updatePost(boardKey, updatedBoard, authToken);

        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/post/{boardKey}")
    public ResponseEntity<String> deletePost(
            @PathVariable String boardKey,
            @RequestHeader("Authorization") String authToken) {

        boardService.deletePost(boardKey, authToken);

        return ResponseEntity.ok("Post deleted successfully");
    }




}
