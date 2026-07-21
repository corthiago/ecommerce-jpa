package com.thiago.ecommerce.service;

import com.thiago.ecommerce.controller.dto.CreateUserDto;
import com.thiago.ecommerce.entity.BillingAddressEntity;
import com.thiago.ecommerce.entity.UserEntity;
import com.thiago.ecommerce.repository.BillingAddressRepository;
import com.thiago.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

        //var savedBillingAddress = billingAddressRepository.save(billingAddress);

        var user = new UserEntity();
        user.setFullName(dto.fullName());
        user.setBillingAddress(billingAddress);

        return userRepository.save(user);
    }

    public Optional<UserEntity> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public boolean deleteById(UUID userId) {
        var user = userRepository.findById(userId);

        if(user.isPresent()){
            userRepository.deleteById(userId);
            //billingAddressRepository.deleteById(user.get().getBillingAddress().getBillingAddressId());
        }

        return user.isPresent();
    }
}
