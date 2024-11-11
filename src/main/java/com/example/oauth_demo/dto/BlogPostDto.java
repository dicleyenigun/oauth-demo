package com.example.oauth_demo.dto;

public class BlogPostDto {

    private String title;
    private String content;
   private String author;

    // Getters ve Setters
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

    public String getAuthor() {
            return author;
        }

    public void setAuthor(String author) {
        this.author = author;
    }
}
