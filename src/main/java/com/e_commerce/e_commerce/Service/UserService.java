package com.e_commerce.e_commerce.Service;

import java.util.List;

import com.e_commerce.e_commerce.Entity.Address;
import com.e_commerce.e_commerce.Entity.User;

public interface UserService {

    void saveUser(User user);

    void updateUserusername(Integer id, String username);

    List<Address> getAddressesByUserId(Integer id);

    User getUserbyId(Integer id);

    User getUserbyEmail(String email);

    User getUserbyPassword(String password);
}
