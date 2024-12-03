package org.example.authproj.Service;

import org.example.authproj.model.Photo;
import org.example.authproj.model.User;
import org.example.authproj.repository.PhotoRepository;
import org.example.authproj.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhotoRepository photoRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.photoRepository = photoRepository;
    }
    public void registerNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }



    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
