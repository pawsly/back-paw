package com.example.pawsly.UserBoard.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private String boardKey;
    private String title;
    private String content;
    private String writer;
    private String nickname;
    private String secret; //비밀글 여부
    private String boardState; //임시글 여부
    private LocalDateTime createdBd; //게시물 작성일


    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getBoardKey() {
        return boardKey;
    }
    public void setBoardKey(String boardKey) {
        this.boardKey = boardKey;
    }

}

