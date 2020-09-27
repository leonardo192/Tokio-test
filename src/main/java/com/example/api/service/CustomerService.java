package com.example.api.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private static final String CUSTOMER_NOT_FOUND = "Cliente de id: %d não encontrado";
	
	private CustomerRepository repository;

	@Autowired
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public List<Customer> findAll() {
		return repository.findAllByOrderByNameAsc();
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public Customer save(Customer customer) {
		return repository.save(customer);
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND, id));
		}
	}
	
	public Customer findOrFail(Long id) {
		return repository.findById(id).orElseThrow(()-> 
					new EntityNotFoundException(String.format(CUSTOMER_NOT_FOUND, id)));
	}

}
