
package com.example.invoice;

import com.example.invoice.repository.InvoiceRepository;
import com.example.invoice.service.InvoiceService;
import org.junit.jupiter.api.Test;

import com.example.invoice.model.Invoice;
import com.example.invoice.model.InvoiceStatus;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceServiceTest {

    @Test
    void testCreateInvoice() {

        InvoiceRepository repo = new InvoiceRepository();
        InvoiceService service = new InvoiceService(repo);

        String id = service.createInvoice(100, LocalDate.now());

        assertNotNull(id);
        assertFalse(repo.findById(id).isEmpty());
    }

    @Test
    void testPayInvoiceFully() {
        InvoiceRepository repo = new InvoiceRepository();
        InvoiceService service = new InvoiceService(repo);

        String id = service.createInvoice(200, LocalDate.now().plusDays(1));
        service.payInvoice(id, 200);

        assertEquals(InvoiceStatus.PAID, repo.findById(id).get().getStatus());
    }

    @Test
    void testProcessOverdueCreatesNewInvoice() {
        InvoiceRepository repo = new InvoiceRepository();
        InvoiceService service = new InvoiceService(repo);

        // create an invoice due yesterday
        String id = service.createInvoice(100, LocalDate.now().minusDays(1));
        // no payment made

        service.processOverdue(10.0, 0);

        // original should be voided
        assertEquals(InvoiceStatus.VOID, repo.findById(id).get().getStatus());
        // a new invoice should exist with amount+lateFee
        assertTrue(repo.findAll().stream()
                .anyMatch(inv -> inv.getAmount() == 110.0 && inv.getStatus() == InvoiceStatus.PENDING));
    }

    @Test
    void testCreateInvoiceNullDueDateThrows() {
        InvoiceRepository repo = new InvoiceRepository();
        InvoiceService service = new InvoiceService(repo);

        assertThrows(IllegalArgumentException.class,
                () -> service.createInvoice(50, null));
    }

    @Test
    void testProcessOverdueSkipsNullDueDate() {
        InvoiceRepository repo = new InvoiceRepository();
        InvoiceService service = new InvoiceService(repo);

        // manually add an invoice with null due date to simulate bad data
        Invoice bad = new Invoice("bad", 100, null);
        repo.save(bad);

        // should not throw NPE
        service.processOverdue(5, 1);

        // bad invoice remains unchanged
        assertNull(repo.findById("bad").get().getDueDate());
    }
}
