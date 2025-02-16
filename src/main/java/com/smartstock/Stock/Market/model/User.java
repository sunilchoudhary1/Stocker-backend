package com.smartstock.Stock.Market.model;

import com.smartstock.Stock.Market.DTO.UserRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
//@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   // @Column(nullable = false, unique = true)
    private String username;

  //  @Column(nullable = false, unique = true)
    private String email;

   // @Column(nullable = false)
    private String password;

//    public User(UserRequestDto dto) {
//        this.username = dto.getUsername();
//        this.email = dto.getEmail();
//        this.password = dto.getPassword(); // Hash before saving in real applications
//    }


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Portfolio> portfolios; // List of portfolios owned by the user>
}
