package com.example.api.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import com.example.api.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;
	
	private CustomerService service;

	
	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	/*
	 * @GetMapping public List<Customer> findAll() { return service.findAll(); }
	 */

	/*
	 * @GetMapping("/{id}") public Customer findById(@PathVariable Long id) { return
	 * service.findById(id) .orElseThrow(() -> new
	 * ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")); }
	 */
	
	@GetMapping
	public List<Customer> findAll(Pageable page) {
		Page<Customer> pages = repository.findAll(page);
		return pages.getContent();
	}

	@GetMapping("{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findOrFail(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public void save(@RequestBody @Valid Customer customer) {
		service.save(customer);
		
	}
	

	@PutMapping("{id}")
	public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody @Validated Customer customer) {
		Customer currentCustomer = service.findOrFail(id);

		BeanUtils.copyProperties(customer, currentCustomer, "id");

		service.save(currentCustomer);

		return ResponseEntity.status(HttpStatus.OK).body(currentCustomer);
		
	}
	

	@DeleteMapping("{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
		
	}
	

}
