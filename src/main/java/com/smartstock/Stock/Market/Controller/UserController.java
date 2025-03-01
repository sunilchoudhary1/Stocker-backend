package com.smartstock.Stock.Market.Controller;


import com.smartstock.Stock.Market.DTO.LoginRequestdto;
import com.smartstock.Stock.Market.DTO.UserRequestDto;
import com.smartstock.Stock.Market.Repository.UserRepository;
import com.smartstock.Stock.Market.Service.UserService;
import com.smartstock.Stock.Market.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api") // Base URL
@CrossOrigin(origins = "*")  // Allow CORS

@Slf4j

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean registered = userService.registerUser(user);
        if (!registered) {
            return ResponseEntity.badRequest().body("Email already taken");
        }
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestdto request) {
        User user = userRepository.findUserByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user !=null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", "fake-jwt-token"); // Replace with actual JWT logic
            response.put("message", "Login successful"); // Ensure response is JSON
            return ResponseEntity.ok(response);
        }
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }
}