package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Address;
import com.example.api.exception.BusinessException;
import com.example.api.exception.EntityNotFoundException;
import com.example.api.repository.AddressRepository;
import com.example.api.service.AdressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AdressService addressService;
	

	@Autowired
	private AddressRepository repository;
	
	@GetMapping
	public List<Address> findALL() {
		return repository.findAll();
	}
	
	@GetMapping("{cep}")
	public Address getByCep(@PathVariable String cep) throws Exception{
		return addressService.findCep(cep);
	}
	
	
	@PostMapping
	public Address save(@RequestBody Address address) {
		try {
			return addressService.save(address);
		}catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}
	}
	

}
