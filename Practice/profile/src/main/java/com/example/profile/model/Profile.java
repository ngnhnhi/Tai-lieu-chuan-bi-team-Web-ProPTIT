package com.example.profile.model;

import java.util.List;

public class Profile {
    private String fullName;
    private String title;
    private String bio;
    private String email;
    private String github;
    private List<String> skills;

    // 1. Constructor không tham số
    public Profile() {
    }

    // 2. Constructor đầy đủ tham số để tiện khởi tạo dữ liệu mẫu
    public Profile(String fullName, String title, String bio, String email, String github, List<String> skills) {
        this.fullName = fullName;
        this.title = title;
        this.bio = bio;
        this.email = email;
        this.github = github;
        this.skills = skills;
    }

    // 3. Getter và Setter (BẮT BUỘC phải có để Thymeleaf có thể đọc được dữ liệu)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}