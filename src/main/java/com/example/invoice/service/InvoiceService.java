
package com.example.invoice.service;

import com.example.invoice.model.*;
import com.example.invoice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    public String createInvoice(double amount, LocalDate dueDate) {
        // validate input
        if (dueDate == null) {
            throw new IllegalArgumentException("dueDate must not be null");
        }

        String id = UUID.randomUUID().toString();
        Invoice invoice = new Invoice(id, amount, dueDate);

        repository.save(invoice);

        return id;
    }

    public Collection<Invoice> getInvoices() {
        return repository.findAll();
    }

    public void payInvoice(String id, double amount) {

        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.addPayment(amount);
    }

    public void processOverdue(double lateFee, int overdueDays) {

        LocalDate today = LocalDate.now();

        for (Invoice invoice : repository.findAll()) {
            // skip invoices with missing due date to avoid NPE
            if (invoice.getDueDate() == null) {
                continue;
            }

            if (invoice.getStatus() == InvoiceStatus.PENDING &&
                    today.isAfter(invoice.getDueDate().plusDays(overdueDays))) {

                double remaining = invoice.getAmount() - invoice.getPaidAmount();

                if (invoice.getPaidAmount() > 0) {

                    invoice.setStatus(InvoiceStatus.PAID);

                    createInvoice(
                            remaining + lateFee,
                            today.plusDays(overdueDays)
                    );

                } else {

                    invoice.setStatus(InvoiceStatus.VOID);

                    createInvoice(
                            invoice.getAmount() + lateFee,
                            today.plusDays(overdueDays)
                    );
                }
            }
        }
    }
}
