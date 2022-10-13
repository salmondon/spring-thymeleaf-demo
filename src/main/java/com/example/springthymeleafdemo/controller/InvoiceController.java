package com.example.springthymeleafdemo.controller;

import com.example.springthymeleafdemo.entity.Customer;
import com.example.springthymeleafdemo.entity.Invoice;
import com.example.springthymeleafdemo.service.CustomerRepository;
import com.example.springthymeleafdemo.service.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class InvoiceController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @ResponseBody
    @GetMapping(path = "/api/invoice/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable(value = "id") long id) {
        Optional<Invoice> response = invoiceRepository.findById(id);
        if (response.isPresent()) {
            return new ResponseEntity<>(response.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ResponseBody
    @GetMapping(path = "/api/invoice")
    public Iterable<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @ResponseBody
    @PostMapping(path = "/api/customer/{id}/invoice")
    public ResponseEntity<Invoice> addNewInvoice(@PathVariable(value = "id") long id, @RequestBody Invoice invoiceRequest) {
        Optional<Customer> c = customerRepository.findById(id);
        if (c.isPresent()) {
            invoiceRequest.setCustomer(c.get());
            invoiceRequest.setDate(LocalDate.now().toString());
            invoiceRepository.save(invoiceRequest);
            return new ResponseEntity<>(invoiceRequest, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ResponseBody
    @DeleteMapping("/api/invoice/{id}")
    public ResponseEntity<HttpStatus> deleteInvoiceById(@PathVariable("id") long id) {
        invoiceRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
