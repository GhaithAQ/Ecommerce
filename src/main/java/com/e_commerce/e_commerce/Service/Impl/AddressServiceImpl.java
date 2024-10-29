package com.e_commerce.e_commerce.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce.e_commerce.Entity.Address;
import com.e_commerce.e_commerce.Entity.User;
import com.e_commerce.e_commerce.Repository.AddressRepository;
import com.e_commerce.e_commerce.Service.AddressService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional
    public void AddAddressesTouser(Address address, User user) {

        entityManager.merge(user);
        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void saveAddresses(User user) {
        for (Address address : user.getAddresses()) {
            entityManager.merge(address);

        }
        addressRepository.saveAll(user.getAddresses());
    }

    @Override
    @Transactional
    public void saveAddress(Address address, User user) {
        address.setUser(entityManager.merge(user));
        addressRepository.save(address);
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AddressRepository getAddressRepository() {
        return this.addressRepository;
    }

    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

}
