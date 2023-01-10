package org.yhao3.crudbestpractice.service.dto;

import jakarta.validation.constraints.Min;

/**
 * @author yhao3
 */
public class BillDetailPartialUpdateDTO {

    private Long id;

    @Min(0)
    private Double debitAmt;

    @Min(0)
    private Double creditAmt;

    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

//    @Override
//    public String toString() {
//        return "BillDetailPartialUpdateDTO{" +
//                "id=" + id +
//                ", debitAmt=" + debitAmt +
//                ", creditAmt=" + creditAmt +
//                ", memo='" + memo + '\'' +
//                '}';
//    }
}
