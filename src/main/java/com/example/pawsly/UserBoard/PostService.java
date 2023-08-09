package com.example.pawsly.UserBoard;

import com.example.pawsly.User.User;
import com.example.pawsly.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(UUID userkey, String nickname,String title, String content) {
        Optional<User> userOptional = userRepository.findByUserkey(userkey);
        if (userOptional.isEmpty()) {
            // 사용자가 없는 경우 예외 처리 또는 적절한 처리 방법 선택
            return null;
        }

        User user = userOptional.get(); // Optional에서 User 객체 가져오기

        Post post = Post.builder()
                .userkey(user.getUserkey()) // 사용자의 userkey 사용
                .title(title)
                .content(content)
                .nickname(user.getNickname())
                .build();

        return postRepository.save(post);
    }

    public Post updatePost(Long postId, String title, String content) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            // 게시물이 없는 경우 예외 처리 또는 적절한 처리 방법 선택
            return null;
        }

        Post post = postOptional.get();
        post.setTitle(title);
        post.setContent(content);

        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
