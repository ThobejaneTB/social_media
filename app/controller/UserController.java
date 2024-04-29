package teleki.socialmedia.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teleki.socialmedia.app.model.User;
import teleki.socialmedia.app.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public void createUser(@RequestBody User user) {
        userService.createNewUser(user);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok( userService.fetchUser(id));
    }
}
