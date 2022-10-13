package com.example.springthymeleafdemo.service;

import com.example.springthymeleafdemo.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
