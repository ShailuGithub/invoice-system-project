
package com.example.invoice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OverdueRequest {

    @NotNull
    @Min(0)
    private Double late_fee;

    @NotNull
    @Min(0)
    private Integer overdue_days;

    public Double getLate_fee() { return late_fee; }
    public Integer getOverdue_days() { return overdue_days; }

}
