
package com.example.invoice.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateInvoiceRequest {

    private double amount;
    @NotNull
    private LocalDate due_date;

    public double getAmount() { return amount; }
    public LocalDate getDue_date() { return due_date; }

}
