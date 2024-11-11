package com.example.oauth_demo.service;

import com.example.oauth_demo.dto.BlogPostDto;
import com.example.oauth_demo.entity.BlogPost;
import com.example.oauth_demo.exception.ResourceNotFoundException;
import com.example.oauth_demo.repository.BlogPostRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    // Blog yazısı oluşturma
    public BlogPost createPost(BlogPostDto blogPostDto, String username) {
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setAuthor(username);  // Kimliği doğrulanan kullanıcı adını ayarla
        return blogPostRepository.save(blogPost);
    }


    // Tüm blog yazılarını getir
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    // ID'ye göre blog yazısını getir
    public BlogPost getPostById(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found with id: " + id));
    }

    // Blog yazısını güncelle
    public BlogPost updatePost(Long id, BlogPostDto blogPostDto) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found with id: " + id));

        // Başlığı ve içeriği güncelle
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setContent(blogPostDto.getContent());

        // Geçerli kullanıcının kullanıcı adını al ve onu yazar olarak ayarla
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        blogPost.setAuthor(currentUsername);

        return blogPostRepository.save(blogPost);
    }

    // Blog yazısını sil
    public void deletePost(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found with id: " + id));
        blogPostRepository.delete(blogPost);
    }
}
