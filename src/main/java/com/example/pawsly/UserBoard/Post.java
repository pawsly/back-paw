package com.example.pawsly.UserBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GeneratedValue
    private UUID userkey;
    private String title;
    private String content;
    private String nickname;


    @Builder
    public Post(Long id, UUID userkey,String title , String content, String nickname){
        this.userkey = UUID.randomUUID();
        this.id=id;
        this.title=title;
        this.content=content;
        this.nickname=nickname;
    }
    public Post() {
    }

    public String getContent() {
        return this.content;
    }
}