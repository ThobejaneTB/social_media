package teleki.socialmedia.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teleki.socialmedia.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
