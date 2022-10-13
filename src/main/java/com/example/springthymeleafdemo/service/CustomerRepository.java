package com.example.springthymeleafdemo.service;

import com.example.springthymeleafdemo.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
