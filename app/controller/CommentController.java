package teleki.socialmedia.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teleki.socialmedia.app.model.Comment;
import teleki.socialmedia.app.service.CommentService;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comment/add")
    public ResponseEntity<List<Comment>> addPostComment(@RequestBody Comment comment, @RequestParam Long commenterId, @RequestParam Long postId) {
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(commentService.createComment(comment, commenterId, postId)) ;
    }

    @GetMapping(value = "/comment/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId)
    {
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @GetMapping(value = "/comments")
    public ResponseEntity<List<Comment>> getAllPostComment(@RequestParam Long postId)
    {
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    @PutMapping(value = "/comment")
    public ResponseEntity<List<Comment>> updateComment(@RequestBody Comment comment ,@RequestParam Long commentId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(commentService.updateComment(comment ,commentId, userId));
    }

    @DeleteMapping(value = "/comment")
    public ResponseEntity<List<Comment>> deleteComment(@RequestParam Long commentId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(commentService.deleteComment(commentId, userId));
    }

    @GetMapping("/page")
    public ResponseEntity<List<Comment>> getPaginatedComments(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentService.getPaginatedComments(pageable);
        return ResponseEntity.ok(comments.getContent());
    }
}
