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
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getContent(),postDto.getNickname());
        Post createdPost = postService.createPost(post);
        PostDto createdPostDto = new PostDto(createdPost.getId(), createdPost.getTitle(), createdPost.getContent(), createdPost.getNickname());
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping("/post/{Id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long Id, @RequestBody PostDto postDto) {
        Post updatedPost = new Post(postDto.getTitle(), postDto.getContent(),postDto.getNickname());
        Post updated = postService.updatePost(Id, updatedPost);
        if (updated != null) {
            PostDto updatedPostDto = new PostDto(updated.getId(), updated.getTitle(), updated.getContent(), updatedPost.getNickname());
            postDto.setTitle(updatedPostDto.getTitle());
            return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/post/{Id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long Id) {
        postService.deletePost(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
