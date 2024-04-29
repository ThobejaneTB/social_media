package teleki.socialmedia.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teleki.socialmedia.app.model.Comment;
import teleki.socialmedia.app.model.Like;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
