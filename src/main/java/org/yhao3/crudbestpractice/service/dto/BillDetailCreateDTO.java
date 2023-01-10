package org.yhao3.crudbestpractice.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * The Bill detail DTO for creating.
 * @author yhao3
 */
public class BillDetailCreateDTO {
    @Min(0)
    @NotNull
    private Double debitAmt;

    @Min(0)
    @NotNull
    private Double creditAmt;

    private String memo;

    public Double getDebitAmt() {
        return debitAmt;
    }

    public void setDebitAmt(Double debitAmt) {
        this.debitAmt = debitAmt;
    }

    public Double getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(Double creditAmt) {
        this.creditAmt = creditAmt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "BillDetailCreateDTO{" +
                "debitAmt=" + debitAmt +
                ", creditAmt=" + creditAmt +
                ", memo='" + memo + '\'' +
                '}';
    }
}
