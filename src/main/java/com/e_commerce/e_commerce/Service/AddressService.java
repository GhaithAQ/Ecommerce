package com.e_commerce.e_commerce.Service;

import com.e_commerce.e_commerce.Entity.Address;
import com.e_commerce.e_commerce.Entity.User;

public interface AddressService {

    void AddAddressesTouser(Address address, User user);

    void saveAddress(Address address, User user);

    void saveAddresses(User user);
}
