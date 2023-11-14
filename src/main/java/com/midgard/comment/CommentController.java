package com.midgard.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentEntity> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentEntity findCommentById(
            @PathVariable("id") Long id
    ) {
        return commentService.findCommentById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CommentResponse> addCommentToTicket(
            @RequestBody CommentRequest newComment
    ) {
        return ResponseEntity.ok(commentService.addNewComment(newComment));
    }
}
