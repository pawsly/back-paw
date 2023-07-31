package com.example.pawsly.UserBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시물 생성
    public Post createPost(Post post) {
        // 게시물 생성 로직 구현
        return postRepository.save(post);
    }

    // 게시물 조회
    public Post getPost(Long postId) {
        // 게시물 조회 로직 구현
        return postRepository.findById(postId).orElse(null);
    }

    // 게시물 수정
    public Post updatePost(Long postId, Post updatedPost) {
        // 게시물 수정 로직 구현
        Post existingPost = postRepository.findById(postId).orElse(null);
        if (existingPost != null) {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setContent(updatedPost.getContent());
            return postRepository.save(existingPost);
        }
        return null;
    }

    // 게시물 삭제
    public void deletePost(Long postId) {
        // 게시물 삭제 로직 구현
        postRepository.deleteById(postId);
    }

}
