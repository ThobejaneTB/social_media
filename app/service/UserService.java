package teleki.socialmedia.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teleki.socialmedia.app.model.User;
import teleki.socialmedia.app.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createNewUser(User user)  {
        user.setUsername(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    public User fetchUser(Long id) {
        return userRepository.findById(id).get();
    }
}
