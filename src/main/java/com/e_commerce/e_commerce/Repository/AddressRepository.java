package com.e_commerce.e_commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.e_commerce.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
