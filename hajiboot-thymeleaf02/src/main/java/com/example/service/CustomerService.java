package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;

	public Page<Customer> findAll (Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	public List<Customer> findAll () {
		return customerRepository.findAllOrderByName();
	}

	public Customer findOne (Integer id) {
		return customerRepository.findOne(id);
	}

	public Customer create (Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer update (Customer customer) {
		return customerRepository.save(customer);
	}

	public void delete (Integer id) {
		customerRepository.delete(id);
	}
}

