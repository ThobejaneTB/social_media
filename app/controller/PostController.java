package teleki.socialmedia.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teleki.socialmedia.app.model.Post;
import teleki.socialmedia.app.model.User;
import teleki.socialmedia.app.service.PostService;
import teleki.socialmedia.app.service.UserService;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @PostMapping(value = "/create/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long userId) {

        User user = userService.fetchUser(userId);

      return  ResponseEntity.ok(postService.createPost(post, user));
    }

    @GetMapping(value = "/post/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId)
    {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping(value = "/posts")
    public ResponseEntity<List<Post>> getAllPostLikes(@RequestParam Long postId,@RequestParam Long userId)
    {
        return ResponseEntity.ok(postService.getAllPosts(postId, userId));
    }

    @PutMapping(value = "/post")
    public ResponseEntity<List<Post>> updatePost(@RequestBody Post post ,@RequestParam Long postId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(postService.updatePost(post ,postId, userId));
    }

    @DeleteMapping(value = "/post")
    public ResponseEntity<List<Post>> deletePost(@RequestBody Post post , @RequestParam Long postId, @RequestParam Long userId)
    {
        return ResponseEntity.ok(postService.deletePost(post,postId, userId));
    }

    @GetMapping(value = "/page")
    public ResponseEntity<List<Post>> getPaginatedPosts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postService.getPaginatedPosts(pageable);
        return ResponseEntity.ok(posts.getContent());
    }
}
