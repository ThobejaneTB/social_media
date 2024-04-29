package teleki.socialmedia.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teleki.socialmedia.app.model.Like;
import teleki.socialmedia.app.service.LikeService;

import java.util.List;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;
    @PostMapping(value = "/like/add")
    public ResponseEntity<List<Like>> addPostComment(@RequestBody Like like, @RequestParam Long likeId, @RequestParam Long postId) {
        return ResponseEntity.ok(likeService.createComment(like, likeId, postId));
    }

    @GetMapping(value = "/like/{likeId}")
    public ResponseEntity<Like> getLike(@PathVariable Long likeId)
    {
        return ResponseEntity.ok(likeService.getLike(likeId));
    }

    @GetMapping(value = "/likes")
    public ResponseEntity<List<Like>> getAllPostLikes(@RequestParam Long postId)
    {
        return ResponseEntity.ok(likeService.getAllLikes(postId));
    }

    @PutMapping(value = "/like")
    public ResponseEntity<List<Like>> updateLike(@RequestBody Like like ,@RequestParam Long likeId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(likeService.updateLike(like ,likeId, userId));
    }

    @DeleteMapping(value = "/like")
    public ResponseEntity<List<Like>> deleteLike(@RequestBody Like like , @RequestParam Long likeId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(likeService.deleteLike(like,likeId, userId));
    }
}
