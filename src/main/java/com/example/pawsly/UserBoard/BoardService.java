package com.example.pawsly.UserBoard;


import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import com.example.pawsly.UserBoard.Dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository,UserRepository userRepository){
        this.boardRepository=boardRepository;
        this.userRepository=userRepository;
    }
    //전체 게시물 출력
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
    //개인피드 출력
    public List<Board> getPostsByWriter(String writer, String userKey) {
        if (writer.equals(userKey)) {
            System.out.println(1);
            return boardRepository.findByWriter(writer);
        } else {
            // 사용자 키와 작성자가 일치하지 않는 경우 처리
            System.out.println("일치하지않음");
            return Collections.emptyList();
        }
    }


    public Board createPost(PostDto postDto, String userKey) throws UsernameNotFoundException {
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

                return board;
            } else {
                throw new UsernameNotFoundException("User not found.");
            }
        }

    public Board updatePost(String boardKey, PostDto postDto, String userKey) {
        Optional<Board> optionalBoard = boardRepository.findById(boardKey);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();

            if (board.getWriter().equals(userKey)) {
                board.setTitle(postDto.getTitle());
                board.setContent(postDto.getContent());
                // ... 기타 필요한 수정 작업 수행
                Board updatedBoard = boardRepository.save(board);
                return updatedBoard;
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have permission to update this post.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found.");
        }
    }

    public String deletePost(String boardKey, String userKey) {
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


