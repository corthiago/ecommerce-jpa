package com.thiago.ecommerce.service;

import com.thiago.ecommerce.controller.dto.CreateUserDto;
import com.thiago.ecommerce.entity.BillingAddressEntity;
import com.thiago.ecommerce.entity.UserEntity;
import com.thiago.ecommerce.repository.BillingAddressRepository;
import com.thiago.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UserEntity createUser(CreateUserDto dto) {
        var billingAddress = new BillingAddressEntity();
        billingAddress.setStreet(dto.address());
        billingAddress.setNumber(dto.number());
        billingAddress.setComplement(dto.complement());

        var savedBillingAddress = billingAddressRepository.save(billingAddress);

        var user = new UserEntity();
        user.setFullName(dto.fullName());
        user.setBillingAddress(savedBillingAddress);

        return userRepository.save(user);
    }
}
