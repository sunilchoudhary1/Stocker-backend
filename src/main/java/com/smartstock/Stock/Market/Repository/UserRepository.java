package com.smartstock.Stock.Market.Repository;

import com.smartstock.Stock.Market.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);

    User findByUsername(String username);

    //User findByUsername(String username);

}
