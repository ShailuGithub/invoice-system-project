
package com.example.invoice.controller;

import com.example.invoice.dto.*;
import com.example.invoice.model.Invoice;
import com.example.invoice.service.InvoiceService;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public Map<String,String> create(@RequestBody @jakarta.validation.Valid CreateInvoiceRequest req) {

        String id = service.createInvoice(req.getAmount(), req.getDue_date());

        return Map.of("id", id);
    }

    @GetMapping
    public Collection<Invoice> list() {
        return service.getInvoices();
    }

    @PostMapping("/{id}/payments")
    public void pay(
            @PathVariable String id,
            @RequestBody PaymentRequest req) {

        service.payInvoice(id, req.getAmount());
    }

    @PostMapping("/process-overdue")
    public void process(@RequestBody OverdueRequest req) {

        service.processOverdue(
                req.getLate_fee(),
                req.getOverdue_days()
        );
    }
}
