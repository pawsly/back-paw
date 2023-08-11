package com.example.pawsly.UserBoard;

import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board createPost(User user, String title, String content, String nickname) {
        Board post = Board.builder()
                .user(user) // 사용자 객체 전달
                .title(title)
                .content(content)
                .nickname(nickname)
                .build();
        System.out.println(user);
        return boardRepository.save(post);
    }

    public Board updatePost(Long boardKey, String title, String content) {
        Optional<Board> postOptional = boardRepository.findByBoardKey(boardKey);
        if (postOptional.isEmpty()) {
            // 게시물이 없는 경우 예외 처리 또는 적절한 처리 방법 선택
            return null;
        }

        Board post = postOptional.get();
        post.setTitle(title);
        post.setContent(content);

        return boardRepository.save(post);
    }

    public void deletePost(Long boardKey) {
        boardRepository.deleteByBoardKey(boardKey);
    }

    public List<Board> getAllPosts() {
        return boardRepository.findAll();
    }
}
