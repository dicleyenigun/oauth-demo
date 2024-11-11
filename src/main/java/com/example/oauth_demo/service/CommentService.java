package com.example.oauth_demo.service;

import com.example.oauth_demo.dto.CommentDto;
import com.example.oauth_demo.entity.BlogPost;
import com.example.oauth_demo.entity.Comment;
import com.example.oauth_demo.entity.User;
import com.example.oauth_demo.exception.ResourceNotFoundException;
import com.example.oauth_demo.repository.BlogPostRepository;
import com.example.oauth_demo.repository.CommentRepository;
import com.example.oauth_demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, BlogPostRepository blogPostRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.blogPostRepository = blogPostRepository;
        this.userRepository =userRepository;
    }

    // Yorum oluştur
    public Comment createComment(CommentDto commentDto) {
        BlogPost post = blogPostRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Geçerli kullanıcının kullanıcı adını SecurityContext'ten alın
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();  // Assuming username is unique

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setPost(post);
        comment.setAuthor(currentUsername);  // Yazarın kullanıcı adını ayarla

        return commentRepository.save(comment);
    }


    // Belirli bir blog yazısına ait yorumları getir
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // Yorum güncelle

    public Comment updateComment(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        // İçeriği güncelle
        comment.setContent(commentDto.getContent());

        // Geçerli kullanıcının kullanıcı adını al ve onu yazar olarak ayarla
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        comment.setAuthor(currentUsername);

        return commentRepository.save(comment);
    }

    // Yorum sil
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }

}

