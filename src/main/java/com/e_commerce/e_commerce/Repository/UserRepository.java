package com.e_commerce.e_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.e_commerce.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail(String email);

    User findByPassword(String password);
}
