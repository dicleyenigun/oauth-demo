package com.example.oauth_demo.controller;

import com.example.oauth_demo.dto.BlogPostDto;
import com.example.oauth_demo.entity.BlogPost;
import com.example.oauth_demo.service.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // Blog yazısı oluştur
    @PostMapping("/create")
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPostDto blogPostDto) {
        // Kimliği doğrulanan kullanıcının username'ini alın
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // BlogPostService'e username'i gönderin
        BlogPost createdPost = blogPostService.createPost(blogPostDto, username);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // Tüm blog yazılarını listele
    @GetMapping
    public ResponseEntity<List<BlogPost>> getAllPosts() {
        List<BlogPost> posts = blogPostService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // ID'ye göre tek bir blog yazısı getir
    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable Long id) {
        BlogPost post = blogPostService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // Blog yazısını güncelle
    @PutMapping("/edit/{id}")
    public ResponseEntity<BlogPost> updatePost(@PathVariable Long id, @RequestBody BlogPostDto blogPostDto) {
        BlogPost updatedPost = blogPostService.updatePost(id, blogPostDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Blog yazısını sil
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        blogPostService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
