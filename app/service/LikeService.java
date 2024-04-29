package teleki.socialmedia.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teleki.socialmedia.app.model.Comment;
import teleki.socialmedia.app.model.Like;
import teleki.socialmedia.app.model.Post;
import teleki.socialmedia.app.model.User;
import teleki.socialmedia.app.repository.CommentRepository;
import teleki.socialmedia.app.repository.LikeRepository;
import teleki.socialmedia.app.repository.PostRepository;
import teleki.socialmedia.app.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    EmailNotification emailNotification;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Like> createComment(Like like ,Long userId, Long postId) {

        Post post = postRepository.findById(postId).get();

        User user = userRepository.findById(userId).get();

       String userPostEmail = post.getUser().getEmail();

        System.out.println(like.isLike());

       String likeMsg = null;
       if (like.isLike()) {
           likeMsg = "Liked";

       } else if (like.isUnLike()) {
           likeMsg = "UnLike";
       }

        emailNotification.senderEmail(userPostEmail,
               "New Like add on your Post",
               "Hi, " + post.getUser().getFullName() + " \n " + user.getFullName() +
                       " has "+ likeMsg +" your post !!" );

        like.setPost(post);
        like.setLikeFullName(user.getFullName());

       likeRepository.save(like);

      return post.getLikes();
    }

    public Like getLike(Long likeId) {
        return likeRepository.findById(likeId)
                .get();
    }

    public List<Like> getAllLikes(Long postId) {
        List<Like> likes = new ArrayList<>();
        List<Like> findAll = likeRepository.findAll();
        for (Like like : findAll) {
            if (like.getPost().getId().equals(postId)) {
                likes.add(like);
            }
        }
        return likes;
    }

    public List<Like> updateLike(Like like, Long likeId, Long userId) {
        List<Like> likes = new ArrayList<>();

        likeRepository.deleteById(likeId);

        for (Like like1 : likeRepository.findAll()) {
            if (like.getPost().getUser().getId().equals(userId)) {
                likes.add(like1);
            }
        }
        return likes;
    }

    public List<Like> deleteLike(Like like,Long likeId, Long userId) {

        List<Like> likeList = new ArrayList<>();
        Like updateLike = likeRepository.findById(likeId).get();
        updateLike.setLike(like.isLike());
        updateLike.setLike(like.isUnLike());
        likeRepository.save(updateLike);

        for (Like like1 : likeRepository.findAll()) {
            if (like.getPost().getUser().getId().equals(userId)) {
                likeList.add(like1);
            }
        }
        return likeList;
    }

}
