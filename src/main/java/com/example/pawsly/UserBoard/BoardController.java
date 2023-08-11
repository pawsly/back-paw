package com.example.pawsly.UserBoard;

import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;



@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final UserRepository userRepository;

    @Autowired
    public BoardController(BoardService boardService,UserRepository userRepository) {
        this.boardService = boardService;
        this.userRepository = userRepository;
    }

    @PostMapping("/post")
    public ResponseEntity<Board> createPost(@RequestBody Board board, @CookieValue(value = "user_key", required = false) Cookie cookie ) {

        Board createdPost = boardService.createPost(board.getUser(), board.getTitle(), board.getContent(), board.getNickname());
        if (createdPost != null) {
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 예외 처리
        }
    }

    @PutMapping("/post/{boardKey}")
    public ResponseEntity<Board> updatePost(@PathVariable Long boardKey, @RequestBody Board board) {
        Board updatedPost = boardService.updatePost(boardKey, board.getTitle(), board.getContent());
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/post/{boardKey}")
    public ResponseEntity<Void> deletePost(@PathVariable Long boardKey) {
        boardService.deletePost(boardKey);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
