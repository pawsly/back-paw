package com.example.pawsly.UserBoard;

import com.example.pawsly.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Board {

    @Id
    @GeneratedValue
    private Long boardKey;
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
    public Board(Long boardKey, String writer, String title , String content, String nickname,String category){
        this.boardKey=boardKey;
        this.title=title;
        this.content=content;
        this.nickname=nickname;
        this.writer=writer;
        this.category=category;
    }
    public Board() {
    }


}