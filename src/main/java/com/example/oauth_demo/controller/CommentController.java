package com.example.oauth_demo.controller;

import com.example.oauth_demo.dto.CommentDto;
import com.example.oauth_demo.entity.BlogPost;
import com.example.oauth_demo.entity.Comment;
import com.example.oauth_demo.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // yorum olusturma
    @PostMapping("/{postId}/create")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        commentDto.setPostId(postId); // Yorumun hangi post'a ait olduğunu belirle
        Comment createdComment = commentService.createComment(commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Belirli bir blog yazısına ait yorumları getir
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Yorum güncelle

    @PutMapping("/edit/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Comment updatedComment = commentService.updateComment(id, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // Yorum sil

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

