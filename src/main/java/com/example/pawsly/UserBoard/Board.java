package com.example.pawsly.UserBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Board {

    @Id
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(unique = true, columnDefinition = "VARCHAR(36)")
    private String boardKey;
    private String writer; //userKey
    private String title;
    private String content;
    private String nickname;
    private String secret; //비밀글 여부
    private String boardState; //임시글 여부
    private LocalDateTime createdBd; //게시물 작성일
    private LocalDateTime lastModifiedBd; //게시물 수정일

    private String category;




    @Builder
    public Board(String boardKey, String writer, String title , String content, String nickname,String category){
        this.boardKey=boardKey;
        this.title=title;
        this.content=content;
        this.nickname=nickname;
        this.writer=writer;
        this.category=category;
    }
    public Board() {
    }
    public String getSecret() {
        return this.secret;
    }

}