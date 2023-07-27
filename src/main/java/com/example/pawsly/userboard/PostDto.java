package com.example.pawsly.userboard;

public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;

    // 생성자, getter, setter 등
    public PostDto(){

    }
    // 생성자
    public PostDto(Long id, String title, String content,String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }

    // getter, setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
