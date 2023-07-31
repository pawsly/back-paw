package com.example.pawsly.UserBoard;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String nickname;

    public Post() {
    }

    public Post(String title, String content,String nickname) {
        this.title = title;
        this.content = content;
        this.nickname=nickname;
    }

    public String getContent() {
        return this.content;
    }
}