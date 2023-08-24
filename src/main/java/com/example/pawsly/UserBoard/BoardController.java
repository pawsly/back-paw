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
    @PostMapping("/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestHeader("Authorization") String authToken) {

        // 작성한 게시물 내용을 생성하여 리턴
        PostDto createdPostDto = boardService.createPost(postDto, authToken);

        createdPostDto.setTitle(postDto.getTitle());
        createdPostDto.setContent(postDto.getContent());
        createdPostDto.setNickname(postDto.getNickname());
        System.out.println(postDto.getNickname()+"+입니다");
        createdPostDto.setSecret(postDto.getSecret());
        createdPostDto.setBoardState(postDto.getBoardState());
        createdPostDto.setWriter(postDto.getWriter());
        System.out.println(postDto.getWriter()+"야");
        createdPostDto.setCreatedBd(LocalDateTime.now());

        return ResponseEntity.ok(createdPostDto);
    }


}
