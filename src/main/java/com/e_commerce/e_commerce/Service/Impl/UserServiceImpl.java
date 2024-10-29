package com.e_commerce.e_commerce.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Address;
import com.e_commerce.e_commerce.Entity.User;
import com.e_commerce.e_commerce.Repository.UserRepository;
import com.e_commerce.e_commerce.Service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userrepository;

    public UserServiceImpl(UserRepository userrepository) {
        this.userrepository = userrepository;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userrepository.save(user);

    }

    @Override
    public User getUserbyId(Integer id) {

        Optional<User> result = userrepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    @Override
    @Transactional
    public void updateUserusername(Integer id, String username) {
        Optional<User> result = userrepository.findById(id);
        if (result.isPresent()) {
            User user = result.get();
            user.setUsername(username);
            userrepository.save(user);
        }
    }

    @Override
    public List<Address> getAddressesByUserId(Integer id) {
        Optional<User> result = userrepository.findById(id);
        if (result.isPresent()) {
            return result.get().getAddresses();
        } else {
            return null;
        }
    }

    @Override
    public User getUserbyEmail(String email) {
        return userrepository.findUserByEmail(email);
    }

    @Override
    public User getUserbyPassword(String password) {
        return userrepository.findByPassword(password);

    }

}
