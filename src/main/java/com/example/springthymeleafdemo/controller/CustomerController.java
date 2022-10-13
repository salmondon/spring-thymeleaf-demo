package com.example.springthymeleafdemo.controller;

import com.example.springthymeleafdemo.CustomerId;
import com.example.springthymeleafdemo.entity.Customer;
import com.example.springthymeleafdemo.service.CustomerRepository;
import com.example.springthymeleafdemo.service.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        CustomerId customerId = new CustomerId();
//        customerId.setId(1);
        model.addAttribute("customerId", customerId);
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }

    @GetMapping("/addnew")
    public String addNewCustomer(Model model) {
        Customer c = new Customer();
        model.addAttribute("customer", c);
        return "newcustomer";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/";
    }

    @GetMapping("/findById")
    public String findCustomerById(Model model, @ModelAttribute(value = "customerId")CustomerId customerId) {
        Optional<Customer> customer = customerRepository.findById(Long.parseLong(customerId.getId()));
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customerid";
        } else {
            return "/redirect:/";
        }
//        CustomerId c = new CustomerId();
//        c.setId(customerId.getId());
//        model.addAttribute("customerId", c);
    }

    @ResponseBody
    @GetMapping(path = "/api/customer")
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping(path = "api/customer/{id}")
    public ResponseEntity<Customer> getById(@PathVariable(value = "id") long id) {
        Optional<Customer> response = customerRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


//
//    @GetMapping(path = "/customer/{id}")
//    public ResponseEntity<Customer> getById(@PathVariable(value = "id") long id) {
//        Optional<Customer> response = customerRepository.findById(id);
//        if (response.isPresent()) {
//            return new ResponseEntity<>(response.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }
//
//    @PostMapping(path = "/customer/add")
//    public Customer addNewCustomer(@RequestBody Customer customerRequest) {
//        Customer c = new Customer();
//        c.setName(customerRequest.getName());
//        c.setTel(customerRequest.getTel());
//        c.setAddress(customerRequest.getAddress());
//
//        customerRepository.save(c);
//        return c;
//    }

}