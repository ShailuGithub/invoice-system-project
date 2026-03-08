
package com.example.invoice.repository;

import com.example.invoice.model.Invoice;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InvoiceRepository {

    private final Map<String, Invoice> storage = new HashMap<>();

    public void save(Invoice invoice) {
        storage.put(invoice.getId(), invoice);
    }

    public Optional<Invoice> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Collection<Invoice> findAll() {
        return storage.values();
    }
}
