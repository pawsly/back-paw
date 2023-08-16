package com.example.pawsly.UserBoard;


import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository,UserRepository userRepository){
        this.boardRepository=boardRepository;
        this.userRepository=userRepository;
    }
    public List<Board> getAllPosts() {
        return boardRepository.findAll();
    }

    public String createPost(PostDto postDto, String userKey) {
        if (!userKey.isEmpty()) {
            User user = userRepository.findByUserKey(userKey);
            if (user != null) {
                // User 엔티티에서 nickname 가져오기
                String nickname = user.getNickname();

                Board board = new Board();
                board.setTitle(postDto.getTitle());
                board.setContent(postDto.getContent());
                board.setNickname(nickname); // User의 nickname 설정
                board.setSecret(postDto.getSecret()); // 비밀글 여부
                board.setBoardState(postDto.getBoardState());
                board.setWriter(userKey); // UUID를 String으로 저장
                boardRepository.save(board);

                return "Post created successfully!";
            } else {
                return "User not found.";
            }
        } else {
            return "No user_key cookie found.";
        }
    }
    public String updatePost(Long boardKey, PostDto postDto, String userKey) {
        Optional<Board> optionalBoard = boardRepository.findById(boardKey);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            if (board.getWriter().equals(userKey)) {
                board.setTitle(postDto.getTitle());
                board.setContent(postDto.getContent());
                // ... 기타 필요한 수정 작업 수행
                boardRepository.save(board);
                return "Post updated successfully!";
            } else {
                return "You don't have permission to update this post.";
            }
        } else {
            return "Post not found.";
        }
    }

    public String deletePost(Long boardKey, String userKey) {
        Optional<Board> optionalBoard = boardRepository.findById(boardKey);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            if (board.getWriter().equals(userKey)) {
                boardRepository.delete(board);
                return "Post deleted successfully!";
            } else {
                return "You don't have permission to delete this post.";
            }
        } else {
            return "Post not found.";
        }
    }
    }


