package com.example.pawsly.UserBoard;

import com.example.pawsly.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Setter
@Getter
public class Board {

    @Id
    @GeneratedValue
    private Long boardKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", referencedColumnName = "userKey")
    private User user;
    private String title;
    private String content;
    private String nickname;



    @Builder
    public Board(Long boardKey, User user, String title , String content, String nickname){
        this.boardKey=boardKey;
        this.title=title;
        this.content=content;
        this.nickname=nickname;
        this.user=user;
    }
    public Board() {
    }


}