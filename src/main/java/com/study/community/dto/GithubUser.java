package com.study.community.dto;

public class GithubUser {
    //唯一值
    private Long id;
    //名称
    private String name;
    //描述
    private String bio;

    public GithubUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
