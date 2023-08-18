package com.example.pawsly.UserBoard;

import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //전체 게시물 리스트 출력 --> 비밀글제외
    @GetMapping("/list")
    public List<Board> getAllPosts() {
        return boardService.getAllPosts();
    }

    //개인 게시물 출력
    @GetMapping("/list/{writer}")
    public List<Board> getPostsByWriter(
            @PathVariable String writer,
            @CookieValue(value = "user_key", defaultValue = "") String userKey
    ) {
        return boardService.getPostsByWriter(writer, userKey);
    }


    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @CookieValue(value = "user_key", defaultValue = "") String userKey) {
        Board createdPost = boardService.createPost(postDto, userKey);

        if (createdPost != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create post.");
        }
    }
    //쿠키인 경우
    @PutMapping("/post/{boardKey}")
    public Board updatePost(
            @PathVariable String boardKey,
            @RequestBody PostDto postDto,
            @CookieValue(value = "user_key", defaultValue = "") String userKey
    ) {
        return boardService.updatePost(boardKey, postDto, userKey);
    }


    @DeleteMapping("/post/{boardKey}")
    public String deletePost(
            @PathVariable String boardKey,
            @CookieValue(value = "user_key", defaultValue = "") String userKey
    ) {
        return boardService.deletePost(boardKey, userKey);
    }
}
