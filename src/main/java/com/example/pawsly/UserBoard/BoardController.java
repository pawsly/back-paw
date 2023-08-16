package com.example.pawsly.UserBoard;

import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/list")
    public List<Board> getAllPosts() {
        return boardService.getAllPosts();
    }

    @PostMapping("/post")
    public String createPost(@RequestBody PostDto postDto, @CookieValue(value = "user_key", defaultValue = "") String userKey) {
        return boardService.createPost(postDto, userKey);
    }
    @PutMapping("/post/{boardKey}")
    public String updatePost(
            @PathVariable Long boardKey,
            @RequestBody PostDto postDto,
            @CookieValue(value = "user_key", defaultValue = "") String userKey
    ) {
        return boardService.updatePost(boardKey, postDto, userKey);
    }

    @DeleteMapping("/post/{boardKey}")
    public String deletePost(
            @PathVariable Long boardKey,
            @CookieValue(value = "user_key", defaultValue = "") String userKey
    ) {
        return boardService.deletePost(boardKey, userKey);
    }
}
