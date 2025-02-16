package com.smartstock.Stock.Market.Service;

import com.smartstock.Stock.Market.DTO.LoginRequestdto;
import com.smartstock.Stock.Market.Repository.UserRepository;
import com.smartstock.Stock.Market.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public boolean registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false; // Email already exists
        }
        user.setPassword((user.getPassword())); // Hash Password
        userRepository.save(user);
        return true;
    }

    // Authenticate User (Login)
    public boolean authenticateUser(LoginRequestdto loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getPassword().equals(loginRequest.getPassword()) && user.getEmail().equals(loginRequest.getEmail())) {
                return true;
            } // Compare Passwords
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}