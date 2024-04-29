package teleki.socialmedia.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teleki.socialmedia.app.model.Post;
import teleki.socialmedia.app.model.User;
import teleki.socialmedia.app.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post, User user) {
        post.setUser(user);
       return postRepository.save(post);
    }

    public Page<Post> getPaginatedPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public List<Post> deletePost(Post post, Long postId, Long userId) {
        List<Post> posts = new ArrayList<>();

        postRepository.deleteById(postId);

        for (Post post1 : postRepository.findAll()) {
            if (post.getUser().getId().equals(userId)) {
                posts.add(post1);
            }
        }
        return posts;

    }

    public List<Post> updatePost(Post post, Long postId, Long userId) {

        List<Post> postList = new ArrayList<>();
        Post updatePost = postRepository.findById(postId).get();
        updatePost.setMessage(post.getMessage());
        postRepository.save(updatePost);

        for (Post post1 : postRepository.findAll()) {
            if (post.getUser().getId().equals(userId)) {
                postList.add(post1);
            }
        }
        return postList;
    }

    public List<Post> getAllPosts(Long postId, Long userId) {
        List<Post> posts = new ArrayList<>();
        for (Post post1 : postRepository.findAll()) {
            if (post1.getUser().getId().equals(userId)) {
                posts.add(post1);
            }
        }
        return posts;
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .get();
    }
}
