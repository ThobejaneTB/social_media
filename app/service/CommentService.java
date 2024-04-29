package teleki.socialmedia.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teleki.socialmedia.app.model.Comment;
import teleki.socialmedia.app.model.Post;
import teleki.socialmedia.app.model.User;
import teleki.socialmedia.app.repository.CommentRepository;
import teleki.socialmedia.app.repository.PostRepository;
import teleki.socialmedia.app.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    EmailNotification emailNotification;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> createComment(Comment comment, Long userId, Long postId) {

        Post post = postRepository.findById(postId).get();

        User user = userRepository.findById(userId).get();

       String userPostEmail = post.getUser().getEmail();

       emailNotification.senderEmail(userPostEmail,
               "New comment has been added on your Post",
               "Hi, " + post.getUser().getFullName() + " \n " + user.getFullName() +
                       " just commented on you post !!" );

        comment.setPost(post);

        comment.setCommenterFullName(user.getFullName());

        commentRepository.save(comment);

      return post.getComments();
    }

    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .get();
    }

    public List<Comment> getAllComments(Long postId) {
        List<Comment> comments = new ArrayList<>();
        List<Comment> findAll = commentRepository.findAll();
        for (Comment comment : findAll) {
            if (comment.getPost().getId().equals(postId)) {
                comments.add(comment);
            }
        }
        return comments;
    }

    public List<Comment> deleteComment(Long commentId, Long userId) {
        List<Comment> comments = new ArrayList<>();

        commentRepository.deleteById(commentId);

        for (Comment comment : commentRepository.findAll()) {
            if (comment.getPost().getUser().getId().equals(userId)) {
                comments.add(comment);
            }
        }
        return comments;
    }

    public List<Comment> updateComment(Comment comment, Long commentId, Long userId) {

        List<Comment> commentList = new ArrayList<>();
        Comment updateComment = commentRepository.findById(commentId).get();
        updateComment.setComment(comment.getComment());
        commentRepository.save(updateComment);

        for (Comment comment1 : commentRepository.findAll()) {
            if (comment.getPost().getUser().getId().equals(userId)) {
                commentList.add(comment1);
            }
        }
         return commentList;
    }

    public Page<Comment> getPaginatedComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
