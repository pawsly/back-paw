package com.example.pawsly.UserBoard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post.getUserkey(), post.getTitle(), post.getContent(), post.getNickname());

        if (createdPost != null) {
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 예외 처리
        }
    }

    @PutMapping("/post/{Id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long Id, @RequestBody Post post) {
        Post updatedPost = postService.updatePost(Id, post.getTitle(), post.getContent());
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/post/{Id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long Id) {
        postService.deletePost(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
