package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
