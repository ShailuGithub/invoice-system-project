
package com.example.invoice.model;

import java.time.LocalDate;

public class Invoice {

    private String id;
    private double amount;
    private double paidAmount;
    private LocalDate dueDate;
    private InvoiceStatus status;

    public Invoice(String id, double amount, LocalDate dueDate) {
        this.id = id;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paidAmount = 0;
        this.status = InvoiceStatus.PENDING;
    }

    public String getId() { return id; }
    public double getAmount() { return amount; }
    public double getPaidAmount() { return paidAmount; }
    public LocalDate getDueDate() { return dueDate; }
    public InvoiceStatus getStatus() { return status; }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public void addPayment(double payment) {
        this.paidAmount += payment;
        if (paidAmount >= amount) {
            status = InvoiceStatus.PAID;
        }
    }
}
